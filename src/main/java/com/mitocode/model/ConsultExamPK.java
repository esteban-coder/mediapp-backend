package com.mitocode.model;

import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@EqualsAndHashCode
@Embeddable
public class ConsultExamPK implements Serializable {

    @EqualsAndHashCode.Include
    @ManyToOne
    @JoinColumn(name = "id_consult")
    private Consult consult;

    @EqualsAndHashCode.Include
    @ManyToOne
    @JoinColumn(name = "id_exam")
    private Exam exam;




}
