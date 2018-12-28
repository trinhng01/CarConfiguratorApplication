package server;
import java.util.*;

import exception.AutoException;
import model.*;

public interface AutoServer {
	public void addAuto(Properties props) throws AutoException;
}
