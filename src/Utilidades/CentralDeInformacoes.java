
/**
 *@author Mycaell
 */

package Utilidades;


import java.util.ArrayList;

import Exce��es.UsuarioExistenteException;
import Exce��es.UsuarioNaoExistenteException;

public class CentralDeInformacoes{

/**
 * atributo que representa uma lista contendo todos os us�rios cadastrados no sistema
 */
	private ArrayList<Usuario> usuariosCadastrados = new ArrayList<Usuario>();
	

/**
 * @return
 * retorna uma lista representando os usu�rios cadastrados
 */	
	public ArrayList<Usuario> getUsuariosCadastrados() {
		return usuariosCadastrados;
	}

/**
 * @param usuariosCadastrados
 * ArrayList que representa os usu�rios cadastrados
 * 
 */
	
	public void setUsuariosCadastrados(ArrayList<Usuario> usuariosCadastrados) {
		this.usuariosCadastrados = usuariosCadastrados;
	}

/**
 * @param usuario 
 * usu�rio que ser� adicionado na lista de usu�rios cadastrados do sistema
 * 
 * @throws UsuarioExistenteException
 * exce��o que representa que existencia desse mesmo usu�rio do parametro na lista de usu�rios cadastrados
 * 
 */	

	public void adicionarUsuario(Usuario usuario) throws UsuarioExistenteException{
		for (Usuario u : usuariosCadastrados) {
			if(u.getEmail().equals(usuario.getEmail())) {
				throw new UsuarioExistenteException();
			}
		}
		usuariosCadastrados.add(usuario);
	}

/**
 * @param email
 * String que representa o e-mail do usu�rio
 * @param senha
 * String que representa a senha do usu�rio
 *  
 *  @throws UsuarioNaoExistenteException
 *  exce��o que representa que n�o existe um usu�rio cadastrado que tenha como e-mail e senha o valores passados como par�metro
 *  
 *  @return Usuario
 *  ap�s uma busca na lista de usu�rios cadastrados, se for encontrado algum usu�rio oque tenha  o e-mail e senha iguais aos par�metros de entrada esse usu�rio ser� retornado
 */	
	
	public Usuario recuperarUsuario(String email, String senha) throws UsuarioNaoExistenteException{
		
		for (Usuario usuario: usuariosCadastrados) {
			if(usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
				return usuario;
			}
		}
		
		throw new UsuarioNaoExistenteException();
		
	}

/**
 * @param email
 * variavel que representa o email de um usu�rio
 * 
 * @return Usuario
 * 
 * ap�s uma busca na lista de usu�rios cadastrados, se for encontrado algum usu�rio que tenha o mesmo e-mail do par�metro de entrada ele ser� retornado
 */	
	public Usuario recuperarUsuario(String email) {
		for (Usuario u : usuariosCadastrados) {
			if(u.getEmail().equals(email)){
				return u;
			}
		}
		
		return null;
	}
	
}
