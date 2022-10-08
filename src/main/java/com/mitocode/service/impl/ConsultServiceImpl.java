package com.mitocode.service.impl;

import com.mitocode.dto.ConsultProcDTO;
import com.mitocode.model.Consult;
import com.mitocode.model.Exam;
import com.mitocode.repo.IConsultExamRepo;
import com.mitocode.repo.IConsultRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.service.IConsultService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConsultServiceImpl extends CRUDImpl<Consult, Integer> implements IConsultService {

    /*@Value("${reportUrl}")
    private String reportUrl;*/

    @Autowired
    private IConsultRepo consultRepo;

    @Autowired
    private IConsultExamRepo ceRepo;

    @Override
    protected IGenericRepo<Consult, Integer> getRepo() {
        return consultRepo;
    }

    @Transactional
    @Override
    public Consult saveTransactional(Consult consult, List<Exam> exams) {
        //Consult + ConsultDetail
        //@JsonManagedReference y @JsonBackReference ya no requiere referencia de detalle hacia consulta
        //consult.getDetails().forEach(det -> det.setConsult(consult));
        consultRepo.save(consult);

        //ConsultExam
        exams.forEach(ex -> ceRepo.saveExam(consult.getIdConsult(), ex.getIdExam()));

        return consult;
    }

    @Override
    public List<Consult> search(String dni, String fullname) {
        return consultRepo.search(dni, fullname);
    }

    @Override
    public List<Consult> searchByDates(LocalDateTime date1, LocalDateTime date2) {
        return consultRepo.searchByDates(date1, date2);
    }

    @Override
    public List<ConsultProcDTO> callProcedureOrFunction() {
        //List<Object[]>
        //[4	"02/07/2022"]
        //[1	"22/07/2022"]
        //[11  "23/07/2022"]
        List<ConsultProcDTO> consults = new ArrayList<>();

        consultRepo.callProcedureOrFunction().forEach(x -> {
            ConsultProcDTO dto = new ConsultProcDTO();
            dto.setQuantity((Integer) x[0]);
            dto.setConsultdate((String) x[1]);
            consults.add(dto);
        });

        return consults;
    }

    @Override
    public byte[] generateReport() throws Exception {
        byte[] data = null;

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("txt_title", "Report Title");

        File file;

        file = new ClassPathResource("/reports/consultas.jasper").getFile();
        JasperPrint print = JasperFillManager.fillReport(file.getPath(), parameters, new JRBeanCollectionDataSource(callProcedureOrFunction()));
        data = JasperExportManager.exportReportToPdf(print);

        return data;
    }
}
