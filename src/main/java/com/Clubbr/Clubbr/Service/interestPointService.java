package com.Clubbr.Clubbr.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Clubbr.Clubbr.Repository.interestPointRepo;
import com.Clubbr.Clubbr.Repository.eventRepo;
import org.springframework.transaction.annotation.Transactional;


@Service
public class interestPointService {

    @Autowired
    private interestPointRepo interestPointRepo;

    @Autowired
    private eventRepo eventRepo;

    //@Transactional
    //public void addSpecificInterestPointToEvent(event )
}
