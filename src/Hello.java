import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.youtube.vitess.vtgate.KeyRange;
import com.youtube.vitess.vtgate.Row;
import com.youtube.vitess.vtgate.VtGate;
import com.youtube.vitess.vtgate.Query.QueryBuilder;
import com.youtube.vitess.vtgate.cursor.Cursor;

/**
 * Servlet implementation class Hello
 */
public class Hello extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Hello() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		try {

			VtGate mCon = VtGate.connect("130.211.148.31:15001", 20000);

			mCon.begin();

			QueryBuilder mBuilder = new QueryBuilder("select * from zone",
					"test_keyspace", "replica").addKeyRange(KeyRange.ALL);

			Cursor mCursor = mCon.execute(mBuilder.build());

			while (mCursor.hasNext()) {
				Row mRow = mCursor.next();

				Object obj = mRow.getObject("display_name");

				String str = new String((byte[]) obj);

				System.out.println(str);

			}

			mCon.close();
			out.println("Success");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("Failure");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
