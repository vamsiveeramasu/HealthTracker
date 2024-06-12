package org.example;

import org.example.data.RegisterUser;

import javax.swing.*;

class LoginFormDemo {

    //main() method start
    public static void main(String arg[]) {
        try {
            //create instance of the CreateLoginForm
            LoginForm form = new LoginForm();
            RegisterUser user = form.user;
            if (user != null) {
                System.out.println("User successfully authenticated!");
                System.out.println("Email" +  user.email);
                System.out.println("Password" +  user.password);
                System.out.println("Username" +  user.username);

            }
            else {
                System.out.println("Authentication cancelled");

            }

        form.setSize(300, 100);  //set size of the frame
        form.setVisible(true);  //make form visible to the user
    } catch(
    Exception e)

    {
        //handle exception
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}
}
