
/**
 *@author Mycaell
 */

package Utilidades;


import java.util.ArrayList;

import Exceções.UsuarioExistenteException;
import Exceções.UsuarioNaoExistenteException;

public class CentralDeInformacoes{

/**
 * atributo que representa uma lista contendo todos os usários cadastrados no sistema
 */
	private ArrayList<Usuario> usuariosCadastrados = new ArrayList<Usuario>();
	

/**
 * @return
 * retorna uma lista representando os usuários cadastrados
 */	
	public ArrayList<Usuario> getUsuariosCadastrados() {
		return usuariosCadastrados;
	}

/**
 * @param usuariosCadastrados
 * ArrayList que representa os usuários cadastrados
 * 
 */
	
	public void setUsuariosCadastrados(ArrayList<Usuario> usuariosCadastrados) {
		this.usuariosCadastrados = usuariosCadastrados;
	}

/**
 * @param usuario 
 * usuário que será adicionado na lista de usuários cadastrados do sistema
 * 
 * @throws UsuarioExistenteException
 * exceção que representa que existencia desse mesmo usuário do parametro na lista de usuários cadastrados
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
 * String que representa o e-mail do usuário
 * @param senha
 * String que representa a senha do usuário
 *  
 *  @throws UsuarioNaoExistenteException
 *  exceção que representa que não existe um usuário cadastrado que tenha como e-mail e senha o valores passados como parâmetro
 *  
 *  @return Usuario
 *  após uma busca na lista de usuários cadastrados, se for encontrado algum usuário oque tenha  o e-mail e senha iguais aos parâmetros de entrada esse usuário será retornado
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
 * variavel que representa o email de um usuário
 * 
 * @return Usuario
 * 
 * após uma busca na lista de usuários cadastrados, se for encontrado algum usuário que tenha o mesmo e-mail do parâmetro de entrada ele será retornado
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
