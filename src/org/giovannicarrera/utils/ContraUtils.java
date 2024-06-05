/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.giovannicarrera.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author informatica
 */
public class ContraUtils {
    
static private ContraUtils instance;
    private ContraUtils(){
    }
    public static ContraUtils getInstance(){
        if(instance == null){
            instance = new ContraUtils();
        }
        return instance;
    }
    public String encryptedPassword(String passWord){
        return BCrypt.hashpw(passWord, BCrypt.gensalt());
    }
    public boolean checkPassword(String password, String passwordEncrypted){
        return BCrypt.checkpw(password, passwordEncrypted);
    }

}