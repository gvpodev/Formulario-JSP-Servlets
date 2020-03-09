package servlets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;
import beans.BeanUsuario;
import dao.DaoUsuario;

@WebServlet("/salvarUsuario")
@MultipartConfig
public class Usuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoUsuario daoUsuario = new DaoUsuario();

	public Usuario() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			String acao = request.getParameter("acao");
			String user = request.getParameter("user");

			if (acao.equalsIgnoreCase("delete")) {
				daoUsuario.delete(user);
				RequestDispatcher view = request.getRequestDispatcher("/cadastrousuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			} else if (acao.equalsIgnoreCase("editar")) {
				BeanUsuario beanUsuario = daoUsuario.consultar(user);
				RequestDispatcher view = request.getRequestDispatcher("/cadastrousuario.jsp");
				request.setAttribute("user", beanUsuario);
				view.forward(request, response);
			} else if (acao.equalsIgnoreCase("listartodos")) {
				try {
					RequestDispatcher view = request.getRequestDispatcher("/cadastrousuario.jsp");
					request.setAttribute("usuarios", daoUsuario.listar());
					view.forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (acao.equalsIgnoreCase("download")) {
				BeanUsuario usuario = daoUsuario.consultar(user);
				
				if (usuario != null) {
					String tipo = request.getParameter("tipo");
					String contentType = "";
					byte[] fileBytes = null;
					
					if (tipo.equalsIgnoreCase("imagem")) {
						contentType = usuario.getContentType();
						fileBytes = new Base64().decodeBase64(usuario.getFotoBase64());
					} else if (tipo.equalsIgnoreCase("curriculo")) {
						contentType = usuario.getContentTypeCurriculo();
						fileBytes = new Base64().decodeBase64(usuario.getCurriculoBase64());
					}
					
					response.setHeader("Content-Disposition", "attachment;filename=arquivo."
							   + contentType.split("\\/")[1]);
			
					// Coloca os bytes em um objeto de entrada para processamento
					InputStream is = new ByteArrayInputStream(fileBytes);
					// Resposta para o navegador
					int read = 0;
					byte[] bytesSaida = new byte[1024];
					OutputStream os = response.getOutputStream();

					while ((read = is.read(bytesSaida)) != -1) {
						os.write(bytesSaida, 0, read);
					}
					os.flush();
					os.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		String acao = request.getParameter("acao");
		if (acao != null && acao.equalsIgnoreCase("reset")) {
			try {
				RequestDispatcher view = request.getRequestDispatcher("/cadastrousuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			String id = request.getParameter("id");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String nome = request.getParameter("nome");
			String telefone = request.getParameter("telefone");
			String cep = request.getParameter("cep");
			String rua = request.getParameter("rua");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String estado = request.getParameter("estado");
			String ibge = request.getParameter("ibge");

			BeanUsuario usuario = new BeanUsuario();
			usuario.setId(!id.isEmpty() ? Long.parseLong(id) : null);
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setNome(nome);
			usuario.setTelefone(telefone);
			usuario.setCep(cep);
			usuario.setRua(rua);
			usuario.setBairro(bairro);
			usuario.setCidade(cidade);
			usuario.setEstado(estado);
			usuario.setIbge(ibge);

			try {
				if (ServletFileUpload.isMultipartContent(request)) {
					Part imagemFoto = request.getPart("foto");
					if (imagemFoto != null && imagemFoto.getInputStream().available() > 0) {
						String fotoBase64 = new Base64()
								.encodeBase64String(converteStreamParaByte(imagemFoto.getInputStream()));
						usuario.setFotoBase64(fotoBase64);
						usuario.setContentType(imagemFoto.getContentType());
					} else {
						usuario.setFotoBase64(request.getParameter("fotoTemp"));
						usuario.setContentType(request.getParameter("contentFotoTemp"));
					}
					Part curriculoPdf = request.getPart("curriculo");
					if (curriculoPdf != null && curriculoPdf.getInputStream().available() > 0) {
						String curriculoBase64 = new Base64()
								.encodeBase64String(converteStreamParaByte(curriculoPdf.getInputStream()));
						usuario.setCurriculoBase64(curriculoBase64);
						usuario.setContentTypeCurriculo(curriculoPdf.getContentType());
					} else {
						usuario.setCurriculoBase64(request.getParameter("curriculoTemp"));
						usuario.setContentTypeCurriculo(request.getParameter("contentCurriculoTemp"));
					}
				}

				String msg = null;
				boolean podeInserir = true;

				if (login == null || login.isEmpty()) {
					msg = "Login deve ser informado!";
					podeInserir = false;
				} else if (senha == null || senha.isEmpty()) {
					msg = "Senha deve ser informada!";
					podeInserir = false;
				} else if (nome == null || nome.isEmpty()) {
					msg = "Nome deve ser informado!";
					podeInserir = false;
				} else if (cep == null || cep.isEmpty()) {
					msg = "CEP deve ser informado!";
					podeInserir = false;
				} else if (rua == null || rua.isEmpty()) {
					msg = "Rua deve ser informada!";
					podeInserir = false;
				} else if (bairro == null || bairro.isEmpty()) {
					msg = "Bairro deve ser informado!";
					podeInserir = false;
				} else if (cidade == null || cidade.isEmpty()) {
					msg = "Cidade deve ser informada!";
					podeInserir = false;
				} else if (estado == null || estado.isEmpty()) {
					msg = "Estado deve ser informado!";
					podeInserir = false;
				} else if (ibge == null || ibge.isEmpty()) {
					msg = "Código IBGE deve ser informado!";
					podeInserir = false;
				} else if (id == null || id.isEmpty() && !daoUsuario.validarLogin(login)) {
					msg = "Esse login já existe! Tente outro!";
					podeInserir = false;
				} else if (id == null || id.isEmpty() && !daoUsuario.validarSenha(senha)) {
					msg = "Essa senha já existe! Tente outra!";
					podeInserir = false;
				} else if (id == null
						|| id.isEmpty() && daoUsuario.validarLogin(login) && daoUsuario.validarSenha(senha)) {
					try {
						daoUsuario.salvar(usuario);
						msg = "Usuário salvo com sucesso!";
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (id != null && !id.isEmpty() && podeInserir) {
					if (!daoUsuario.validarLoginUpdate(login, id)) {
						request.setAttribute("msg", "Esse login já existe! Vá em EDITAR novamente, e tente outro!");
						podeInserir = false;
					} else if (!daoUsuario.validarSenhaUpdate(senha, id)) {
						request.setAttribute("msg", "Essa senha já existe! Vá em EDITAR novamente, e tente outra!");
						podeInserir = false;
					} else {
						daoUsuario.atualizar(usuario);
						msg = "Usuário editado com sucesso!";
					}
				}

				if (msg != null) {
					request.setAttribute("msg", msg);
				}

				if (!podeInserir) {
					request.setAttribute("user", usuario);
				}
				try {
					RequestDispatcher view = request.getRequestDispatcher("/cadastrousuario.jsp");
					request.setAttribute("usuarios", daoUsuario.listar());
					view.forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}

	// Converte a entrada de fluxo de dados da imagem para um array de bytes
	private byte[] converteStreamParaByte(InputStream imagem) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = imagem.read();
		while (reads != -1) {
			baos.write(reads);
			reads = imagem.read();
		}
		return baos.toByteArray();
	}

}
