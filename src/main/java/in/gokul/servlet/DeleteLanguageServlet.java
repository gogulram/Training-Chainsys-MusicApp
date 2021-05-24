package in.gokul.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.gokul.exception.DbException;
import in.gokul.model.Language;

import in.gokul.services.DeleteLanguageServices;
import in.gokul.validation.LanguageValidator;

/**
 * Servlet implementation class DeleteLanguageServlet
 */
@WebServlet("/DeleteLanguageServlet")
public class DeleteLanguageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteLanguageServlet() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try {
			String language = request.getParameter("deleteLanguage");
			Language inputlanguage = new Language(language);
			if ( (LanguageValidator.isValidLanguage(language))&&(LanguageValidator.validlengthforLanguage(language))&&(DeleteLanguageServices.deleteLanguage(inputlanguage))) {
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("adminWorks.jsp");
				dispatcher.forward(request, response);
			} else {
			    RequestDispatcher dispatcher = request.getRequestDispatcher("adminLogin.jsp?");
				dispatcher.forward(request, response);
			}
		} catch (ServletException | IOException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());
		}
	}


	}

