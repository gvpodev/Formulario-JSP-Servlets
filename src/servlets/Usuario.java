package servlets;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.BeanUsuario;
import dao.DaoUsuario;

@WebServlet("/salvarUsuario")
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
                if(ServletFileUpload.isMultipartContent(request)){
                	List<FileItem> fileItems =  new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                    for (FileItem fileItem : fileItems) {
                        if (fileItem.getFieldName().equals("foto")){
                        	String foto = Base64.encodeBase64String(fileItem.get());
                            System.out.println(foto);
                        }
                    }
                }
                String msg = null;
                boolean podeInserir = true;

                if (login == null || login.isEmpty()){
                    msg = "Login deve ser informado!";
                    podeInserir = false;
                } else if (senha == null || senha.isEmpty()){
                    msg = "Senha deve ser informada!";
                    podeInserir = false;
                } else if (nome == null || nome.isEmpty()){
                    msg = "Nome deve ser informado!";
                    podeInserir = false;
                } else if (telefone == null || telefone.isEmpty()) {
                    msg = "Telefone deve ser informado!";
                    podeInserir = false;
                } else if (cep == null || cep.isEmpty()){
                    msg = "CEP deve ser informado!";
                    podeInserir = false;
                } else if (rua == null || rua.isEmpty()){
                    msg = "Rua deve ser informada!";
                    podeInserir = false;
                } else if (bairro == null || bairro.isEmpty()){
                    msg = "Bairro deve ser informado!";
                    podeInserir = false;
                } else if(cidade == null || cidade.isEmpty()){
                    msg = "Cidade deve ser informada!";
                    podeInserir = false;
                } else if (estado == null || estado.isEmpty()){
                    msg = "Estado deve ser informado!";
                    podeInserir = false;
                } else if (ibge == null || ibge.isEmpty()){
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

}

