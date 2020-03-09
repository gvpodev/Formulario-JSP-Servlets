package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanTelefone;
import beans.BeanUsuario;
import dao.DaoTelefone;
import dao.DaoUsuario;

@WebServlet("/salvarTelefones")
public class TelefoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoUsuario daoUsuario = new DaoUsuario();
	private DaoTelefone daoTelefone = new DaoTelefone();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			BeanUsuario beanUsuario = (BeanUsuario) request.getSession().getAttribute("userEscolhido");
			String numero = request.getParameter("numero");
			String tipo = request.getParameter("tipo");
			String acao = request.getParameter("acao");

			if (acao == null || (acao != null && !acao.equalsIgnoreCase("voltar"))) {

				if (numero == null || numero.isEmpty()) {
					RequestDispatcher view = request.getRequestDispatcher("/telefone.jsp");
					request.setAttribute("telefones", daoTelefone.listar(beanUsuario.getId()));
					request.setAttribute("msg", "Informe o número do telefone!");
					view.forward(request, response);
				} else {
					BeanTelefone beanTelefone = new BeanTelefone();
					beanTelefone.setNumero(numero);
					beanTelefone.setTipo(tipo);
					beanTelefone.setUsuario(beanUsuario.getId());
					daoTelefone.salvar(beanTelefone);
					request.getSession().setAttribute("userEscolhido", beanUsuario);
					request.setAttribute("userEscolhido", beanUsuario);
					RequestDispatcher view = request.getRequestDispatcher("/telefone.jsp");
					request.setAttribute("telefones", daoTelefone.listar(beanUsuario.getId()));
					request.setAttribute("msg", "Número salvo com sucesso!");
					view.forward(request, response);
				}
			} else {
				RequestDispatcher view = request.getRequestDispatcher("/cadastrousuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String user = request.getParameter("user");
			String acao = request.getParameter("acao");
			BeanUsuario beanUsuario = daoUsuario.consultar(user);
			if (acao.equalsIgnoreCase("addFone")) {
				request.getSession().setAttribute("userEscolhido", beanUsuario);
				request.setAttribute("userEscolhido", beanUsuario);
				RequestDispatcher view = request.getRequestDispatcher("/telefone.jsp");
				request.setAttribute("telefones", daoTelefone.listar(beanUsuario.getId()));
				view.forward(request, response);
			} else if (acao.equalsIgnoreCase("deleteFone")) {
				String telefoneId = request.getParameter("telefoneId");
				daoTelefone.delete(telefoneId);
				RequestDispatcher view = request.getRequestDispatcher("/telefone.jsp");
				request.setAttribute("userEscolhido", beanUsuario);
				request.setAttribute("telefones", daoTelefone.listar(beanUsuario.getId()));
				request.setAttribute("msg", "Removido com sucesso!");

				view.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
