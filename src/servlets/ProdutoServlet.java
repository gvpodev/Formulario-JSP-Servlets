package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanProduto;
import dao.DaoProduto;

@WebServlet("/salvarProduto")
public class ProdutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoProduto daoProduto = new DaoProduto();

	public ProdutoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String acao = request.getParameter("acao");
			String produto = request.getParameter("produto");

			if (acao.equalsIgnoreCase("delete")) {
				daoProduto.delete(produto);
				RequestDispatcher view = request.getRequestDispatcher("/cadastroproduto.jsp");
				request.setAttribute("produtos", daoProduto.listar());
				view.forward(request, response);
			} else if (acao.equalsIgnoreCase("editar")) {
				BeanProduto beanProduto = daoProduto.consultar(produto);
				RequestDispatcher view = request.getRequestDispatcher("/cadastroproduto.jsp");
				request.setAttribute("produto", beanProduto);
				view.forward(request, response);
			} else if (acao.equalsIgnoreCase("listartodos")) {
				try {
					RequestDispatcher view = request.getRequestDispatcher("/cadastroproduto.jsp");
					request.setAttribute("produtos", daoProduto.listar());
					view.forward(request, response);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			try {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroproduto.jsp");
				request.setAttribute("produtos", daoProduto.listar());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String quantidade = request.getParameter("quantidade");
			String valor = request.getParameter("valor");

			try {
				String msg = null;
				boolean podeInserir = true;

				if (nome == null || nome.isEmpty()) {
					msg = "Nome deve ser informado!";
					podeInserir = false;
				} else if (quantidade == null || quantidade.isEmpty()) {
					msg = "Quantidade deve ser informada!";
					podeInserir = false;
				} else if (valor == null || valor.isEmpty()) {
					msg = "Valor deve ser informado!";
					podeInserir = false;
				} else if (id == null || id.isEmpty() && !daoProduto.validarNome(nome)) {
					msg = "Produto já cadastrado com esse nome!";
					podeInserir = false;
				}

				BeanProduto beanProduto = new BeanProduto();
				beanProduto.setId(!id.isEmpty() ? Long.parseLong(id) : null);
				beanProduto.setNome(nome);
				if (quantidade != null && !quantidade.isEmpty()) {
					beanProduto.setQuantidade(Double.parseDouble(quantidade));
				}
				if (valor != null && !valor.isEmpty()) {
					beanProduto.setValor(Double.parseDouble(valor));	
				}

				if (id == null || id.isEmpty() && daoProduto.validarNome(nome) && podeInserir) {
					try {
						daoProduto.salvar(beanProduto);
						msg = "Produto salvo com sucesso!";
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (id != null && !id.isEmpty() && podeInserir) {
					if (!daoProduto.validarNomeUpdate(nome, id)) {
						msg = "Produto já cadastrado com esse nome!";
						podeInserir = false;
					} else {
						daoProduto.atualizar(beanProduto);
						msg = "Produto atualizada com sucesso.";
					}
				}

				if (msg != null) {
					request.setAttribute("msg", msg);
				}

				if (!podeInserir) {
					request.setAttribute("produto", beanProduto);
				}

				try {
					RequestDispatcher view = request.getRequestDispatcher("/cadastroproduto.jsp");
					request.setAttribute("produtos", daoProduto.listar());
					view.forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
