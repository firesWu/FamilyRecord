package com.FamilyRecord.untils;

/**
 * Created by yuan on 2018/3/24.
 */
import java.util.UUID;

public class RandomGuid {

    public static String getGuid(){
        String uuid=UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }
    public static void main(String[] args) {
        System.out.println(getGuid());
    }
}
