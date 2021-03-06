package in.gokul.servlet;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.gokul.model.Song;
import in.gokul.services.SongServices;

/**
 * Servlet implementation class AddSongsServlet
 */
@WebServlet("/AddSongsServlet")
public class AddSongsServlet extends HttpServlet {
	private static final String SONG_ERROR_MESSAGE = "adminWorks.jsp?addSongErrorMessage=";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddSongsServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String songName = request.getParameter("songName").toUpperCase();
			String movieName = request.getParameter("movieName").toUpperCase();
			String languageName = request.getParameter("languageName").toUpperCase();
			String releasedOn = request.getParameter("releasedOn");
			Date releasedDate = Date.valueOf(releasedOn);

			Song info = new Song(songName, movieName, releasedDate, languageName);
			if ((SongServices.isValidDetails(info))) {
				if ((!SongServices.isSongAvailable(info))) {
					if (SongServices.addSong(info)) {
						response.sendRedirect("adminWorks.jsp?addSongInfo=" + "succesfully added");
					} else {
						response.sendRedirect(SONG_ERROR_MESSAGE + "Cannot add Song ");
					}
				} else {
					response.sendRedirect(SONG_ERROR_MESSAGE + "Song Already Exists");
				}
			} else {
				response.sendRedirect(SONG_ERROR_MESSAGE + "Invalid details ");
			}

		} catch (Exception e) {

			e.printStackTrace();
			response.sendRedirect(SONG_ERROR_MESSAGE + "Invalid Details");

		}

	}

}
