package Utilidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class Persistencia {

	private XStream xstream = new XStream(new DomDriver("ISO-8859-1"));	
	private File arquivoPadrao;
	
	public void salvarCentral(CentralDeInformacoes central,String nome) {
		
		arquivoPadrao = new File(nome+".xml");
		
		
		String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n";
	    xml += xstream.toXML(central);
		
		
		try {
			PrintWriter gravar = new PrintWriter(arquivoPadrao);
			gravar.print(xml);
			gravar.close();
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public CentralDeInformacoes recuperarCentral(String nome) {

		arquivoPadrao = new File(nome+".xml");
		
		try {
			if(arquivoPadrao.exists()) {
				FileInputStream fis = new FileInputStream(arquivoPadrao);
				return (CentralDeInformacoes) xstream.fromXML(fis);
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
		return new CentralDeInformacoes();

	}
	
}
