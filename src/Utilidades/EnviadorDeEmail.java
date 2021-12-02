package Utilidades;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class EnviadorDeEmail {

	public void enviarEmail( String senha, String remetente, String destinario,String assunto, String msg){
		Properties props = new Properties();
        /** Par�metros de conex�o com servidor Gmail */
        
        props.put("mail.smtp.user", remetente); 
        props.put("mail.smtp.host", "smtp.gmail.com"); 
        props.put("mail.smtp.port", "25"); 
        props.put("mail.debug", "true"); 
        props.put("mail.smtp.auth", "true"); 
        props.put("mail.smtp.starttls.enable","true"); 
        props.put("mail.smtp.EnableSSL.enable","true");

        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
        props.setProperty("mail.smtp.socketFactory.fallback", "false");   
        props.setProperty("mail.smtp.port", "465");   
        props.setProperty("mail.smtp.socketFactory.port", "465");

        Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                         protected PasswordAuthentication getPasswordAuthentication() 
                         {
                               return new PasswordAuthentication(remetente, senha);
                         }
                    });

        /** Ativa Debug para sess�o */
        session.setDebug(true);

        try {

              Message message = new MimeMessage(session);
              message.setFrom(new InternetAddress(remetente)); //Remetente

              Address[] toUser = InternetAddress //Destinat�rio(s)
                         .parse(destinario);  

              message.setRecipients(Message.RecipientType.TO, toUser);
              message.setSubject(assunto);//Assunto
              message.setText(msg);
              /**M�todo para enviar a mensagem criada*/
              Transport.send(message);

//              System.out.println("Feito!!!");

         } catch (MessagingException e) {
        	 JOptionPane.showMessageDialog(null, "Ocorreu alguma falha no envio!", "falha no envio", JOptionPane.ERROR_MESSAGE);
              throw new RuntimeException(e);
        }
	}

	
	
}
