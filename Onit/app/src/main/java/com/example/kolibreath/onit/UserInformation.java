package com.example.kolibreath.onit;

/**
 * Created by kolibreath on 2017/2/3.
 */

public class UserInformation {
    public String username;
    public String userdongtaiStartDate;
    public String userdongtaiContent;
    public String userOnitDeadLine;
    public String userOnitStatus;


    public int userDongtaiWorkingStatus;
    public int userfavaorNumber;
    public int userCommnentNumber;

    public UserInformation(String username,
                           String userdongtaiContent,String userdongtaiStartDate,
                           String userOnitDeadLine, int userCommnentNumber,
                           int userDongtaiWorkingStatus, int userfavaorNumber,
                           String userOnitStatus)
    {
        //this.userAvatarURL = userAvatarURL;
        this.userCommnentNumber = userCommnentNumber;
        this.userdongtaiStartDate = userdongtaiStartDate;
        this.userdongtaiContent = userdongtaiContent;
        this.username = username;
        this.userOnitDeadLine = userOnitDeadLine;
        this.userfavaorNumber =userfavaorNumber;
        this.userDongtaiWorkingStatus = userDongtaiWorkingStatus;
        this.userOnitStatus = userOnitStatus;


    }
    public String getUsername(){
        return username;
    }

    public String getUserdongtaiStartDate(){
        return userdongtaiStartDate;
    }
    //暂时写成 int

    //public int getUserAvatarURL(){
  //      return userAvatarURL;
    //}
    public String getUserdongtaiContent(){
        return userdongtaiContent;
    }
    public String getUserOnitDeadLine(){
        return  userOnitDeadLine;
    }
    public String getUserOnitStatus(){
        return userOnitStatus;
    }
    public int getUserDongtaiWorkingStatus(){
        return userDongtaiWorkingStatus;
    }
    public int getUserfavaorNumber(){
        return userfavaorNumber;
    }
    public int getUserCommnentNumber(){
        return userCommnentNumber;
    }
}
