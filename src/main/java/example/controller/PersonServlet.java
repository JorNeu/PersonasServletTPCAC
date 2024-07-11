package example.controller;

import example.entity.Person;
import example.repository.PersonRepository;
import example.service.PersonService;
import com.google.gson.Gson;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "PersonServlet", urlPatterns = {"/usuarios/*"})
public class PersonServlet extends HttpServlet {
    private PersonService personService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        String url = config.getServletContext().getInitParameter("database.url");
        String username = config.getServletContext().getInitParameter("database.username");
        String password = config.getServletContext().getInitParameter("database.password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            PersonRepository personRepository = new PersonRepository(connection);
            personService = new PersonService(personRepository);
        } catch (SQLException e) {
            throw new ServletException("No se pudo conectar a la base de datos", e);
        } catch (ClassNotFoundException e) {
            throw new ServletException("No se encontró el driver de MySQL", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Person> people = personService.getAllPersons();
            String json = new Gson().toJson(people);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            out.print(json);
            out.flush();
        } catch (SQLException e) {
            throw new ServletException("Error al obtener las personas", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Person person = new Gson().fromJson(req.getReader(), Person.class);
            personService.addPerson(person);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            out.print(new Gson().toJson(person));
            out.flush();
        } catch (SQLException e) {
            throw new ServletException("Error al agregar la persona", e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID requerido");
            return;
        }

        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            personService.deletePerson(id);

            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (SQLException e) {
            throw new ServletException("Error al eliminar la persona", e);
        }
    }
}