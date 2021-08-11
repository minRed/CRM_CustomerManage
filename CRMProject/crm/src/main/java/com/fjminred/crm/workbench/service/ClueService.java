package com.fjminred.crm.workbench.service;

import com.fjminred.crm.workbench.domain.Activity;
import com.fjminred.crm.workbench.domain.Clue;
import com.fjminred.crm.workbench.domain.Tran;

import java.util.List;

public interface ClueService {
    boolean save(Clue clue);

    Clue detail(String id);

    boolean unbund(String id);

    boolean bund(String cid, String[] aids);


    boolean convert(String clueId, Tran t, String createBy);

}
