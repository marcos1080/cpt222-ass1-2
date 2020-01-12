package au.edu.rmit.cpt222.test.ass2;

import au.edu.rmit.cpt222.model.comms.Config;
import au.edu.rmit.cpt222.model.comms.GameEngineServerStub;

public class Server {

	public static void main(String[] args) {
		new GameEngineServerStub(Config.getHostPort());
	}

}
