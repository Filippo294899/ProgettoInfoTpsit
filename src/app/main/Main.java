package app.main;

import app.controller.Controller;
import app.model.Model;

public class Main {

	public static void main(String[] args) {
		Model model = new Model();
		Controller controller = new Controller(model);
		controller.start();

	}

}
