package Utilidades;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class GeradorDeRelatorios {
		
	public void gerarRelatorioDosLeiloesCadastradosPorUmUsuario(Usuario usuario, String nome) {
		
		Document documento = new Document(PageSize.A4, 72,72,72,72);		
		
		try {
			PdfWriter.getInstance(documento, new FileOutputStream(nome+".pdf"));
		
			documento.open();
			
			String criadorDosLeiloes = usuario.getNome()+" "+usuario.getSobrenome();

			Paragraph p1 = new Paragraph("### Leilões criados por "+criadorDosLeiloes+" ###\n");
			documento.add(p1);
			
			for (int i = 0; i < usuario.getLeiloesCadastrados().size(); i++) {
				String leiloes = usuario.getLeiloesCadastrados().get(i).toString();
				Paragraph p2 = new Paragraph(leiloes);
				documento.add(p2);
			}
			
			documento.close();
			
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
	
	}

	public void gerarRelatorioDosLeiloesganhosDeUmUsuario(Usuario usuario, String nome) {

		Document documento = new Document(PageSize.A4, 72,72,72,72);		
		
		try {
			PdfWriter.getInstance(documento, new FileOutputStream(nome+".pdf"));
		
			documento.open();
			
			String criadorDosLeiloes = usuario.getNome()+" "+usuario.getSobrenome();

			Paragraph p1 = new Paragraph("### Leilões ganhos por "+criadorDosLeiloes+" ###\n");
			documento.add(p1);
			
			for (int i = 0; i < usuario.getLeiloesGanhos().size(); i++) {
				String leiloes = usuario.getLeiloesGanhos().get(i).toString();
				Paragraph p2 = new Paragraph(leiloes);
				documento.add(p2);
			}
			
			documento.close();
			
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}

		
		
	}
	
	
	
	
}
