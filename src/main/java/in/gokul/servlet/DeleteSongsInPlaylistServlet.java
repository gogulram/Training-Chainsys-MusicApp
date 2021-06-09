package in.gokul.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.gokul.dto.PlaylistDto;
import in.gokul.exception.DbException;
import in.gokul.model.Playlist;
import in.gokul.services.PlaylistServices;
import in.gokul.validation.PlaylistValidator;
import in.gokul.validation.Validator;

/**
 * Servlet implementation class DeleteSongsInPlaylistServlet
 */
@WebServlet("/DeleteSongsInPlaylistServlet")
public class DeleteSongsInPlaylistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteSongsInPlaylistServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String playlistName = request.getParameter("playListName");
		String songName = request.getParameter("songName");
		String userName = request.getParameter("userName");

		try {
			if (PlaylistValidator.isValidPlaylistName(playlistName) && Validator.stringValidator(songName)) {

				Playlist list1 = new Playlist(playlistName, songName, userName);
				PlaylistDto list = new PlaylistDto(playlistName.trim(), songName.toUpperCase(), userName);
				if ((PlaylistServices.isPlaylistAndSongAlreadyAVailable(list))
						&& (PlaylistServices.deleteSongInPlaylist(list1))) {

					response.sendRedirect("playlist.jsp?info=" + "succesfully deleted");
				} else {
					String errorMessage = "Cannot delete Song from the playlist";
					response.sendRedirect("playlist.jsp?errorMessage=" + errorMessage);
				}
			} else {
				String errorMessage = "Enter Valid details";
				response.sendRedirect("playlist.jsp?errorMessage=" + errorMessage);
			}
		} catch (IOException | DbException e) {
			String errorMessage = "Cannot delete Song in the playlist";
			response.sendRedirect("playlist.jsp?errorMessage=" + errorMessage);

		}
	}

}