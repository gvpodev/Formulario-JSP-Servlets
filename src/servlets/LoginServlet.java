package servlets;

import dao.DaoLogin;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/LoginServlet") // Action no form
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoLogin daoLogin = new DaoLogin();

	public LoginServlet() {
		super();
	}

	// Métodos get e post no form. Vazio = doGet.
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");

			if (daoLogin.validarLogin(login, senha)) {
				// Redirecionamento para uma página
				RequestDispatcher dispatcher = request.getRequestDispatcher("acessoliberado.jsp");
				// request e response padrão do método doPost (parametros)
				dispatcher.forward(request, response);
			} else {
				// Redirecionamento para uma página
				RequestDispatcher dispatcher = request.getRequestDispatcher("acessonegado.jsp");
				// request e response padrão do método doPost (parametros)
				dispatcher.forward(request, response);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
