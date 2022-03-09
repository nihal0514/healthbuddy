package com.example.healthbuddy.chats.chatfragment;

public class Utility {
    private static String alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static int index;
    private static int updated_index;
    private static int final_index;
    private static int index_p_t_l;
    private static int index_s_t_l;
    private static String plaintext;
    private static String ciphertext;
    private static String finaltext;

    public static String encrypt1(String plaintext,int encerptionKey){
        reset();

        plaintext=plaintext.toUpperCase();
        for(index=0;index<plaintext.length();index++){
            if(plaintext.charAt(index)!=' '){
                index_p_t_l=alphabet.indexOf(plaintext.charAt(index));
                updated_index=encerptionKey+alphabet.indexOf(plaintext.charAt(index));

                if(updated_index>=alphabet.length()){
                    final_index=updated_index-alphabet.length();
                }else{
                    final_index=updated_index;
                }
                ciphertext=alphabet.substring(final_index ,final_index +1);
                finaltext=finaltext+ciphertext;

            }
        }
        return finaltext;
    }
    public static String Decrypt1(String ciphertext,int decryptionKey){
        reset();

        ciphertext=ciphertext.toUpperCase();
        for(index=0;index<ciphertext.length();index++){
            if(ciphertext.charAt(index)!=' '){
                index_p_t_l=alphabet.indexOf(ciphertext.charAt(index));
                index_s_t_l=index_p_t_l;
                updated_index=alphabet.indexOf(ciphertext.charAt(index))-decryptionKey;

                if(updated_index<0){
                    final_index=updated_index+alphabet.length();
                }else{
                    final_index=updated_index;
                }
                plaintext=alphabet.substring(final_index ,final_index +1);
                finaltext=finaltext+plaintext;

            }
        }
        return finaltext;
    }
    private static void reset(){
        finaltext="";
    }
}
