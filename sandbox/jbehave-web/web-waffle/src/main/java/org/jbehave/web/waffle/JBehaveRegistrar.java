package org.jbehave.web.waffle;

import static java.io.File.separator;
import static java.lang.System.getProperty;
import static java.util.Arrays.asList;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.codehaus.waffle.io.RequestFileUploader;
import org.codehaus.waffle.menu.Menu;
import org.codehaus.waffle.menu.MenuAwareController;
import org.codehaus.waffle.registrar.AbstractRegistrar;
import org.codehaus.waffle.registrar.Registrar;
import org.codehaus.waffle.view.ViewResolver;
import org.jbehave.scenario.MostUsefulConfiguration;
import org.jbehave.scenario.ScenarioRunner;
import org.jbehave.scenario.parser.PatternScenarioParser;
import org.jbehave.scenario.steps.Steps;
import org.jbehave.web.io.FileZipper;
import org.jbehave.web.io.ZippingFileManager;
import org.jbehave.web.waffle.controllers.FileUploadController;
import org.jbehave.web.waffle.controllers.FilesController;
import org.jbehave.web.waffle.controllers.ScenarioController;

public class JBehaveRegistrar extends AbstractRegistrar {

	public JBehaveRegistrar(Registrar delegate) {
		super(delegate);
	}

	@Override
	public void application() {
		registerMenu();
		registerConfiguration();
		registerScenarioParser();
		registerScenarioRunner();
		registerSteps();
		registerFileManager();
		register("scenario/scenario", ScenarioController.class);
		register("data/files", FilesController.class);
		configureViews();
	}

	@Override
	public void request() {
		register(DiskFileItemFactory.class);
		register(RequestFileUploader.class);
		register("data/upload", FileUploadController.class);
	}

	protected void registerMenu() {
		register("home", MenuAwareController.class);
		registerInstance("menu", createMenu());
	}

	protected Menu createMenu() {
		Map<String, List<String>> content = new HashMap<String, List<String>>();
		content.put("Home", asList("Home:home"));
		content.put("Data", asList("Files:data/files", "Upload:data/upload"));
		content.put("Scenario", asList("Run Scenario:scenario/scenario"));
		return new Menu(content);
	}

	protected void configureViews() {
		ViewResolver viewResolver = getComponentRegistry().locateByKey(ViewResolver.class);
		viewResolver.configureView("home", "ftl/home");
		viewResolver.configureView("data/files", "ftl/data/files");
		viewResolver.configureView("data/upload", "ftl/data/upload");
		viewResolver.configureView("scenario/scenario", "ftl/scenario/scenario");
	}

	protected void registerConfiguration() {
		register(MostUsefulConfiguration.class);
	}

	protected void registerScenarioParser() {
		register(PatternScenarioParser.class);
	}

	protected void registerScenarioRunner() {
		register(ScenarioRunner.class);
	}

	protected void registerSteps() {
		register(Steps.class);
	}

	protected void registerFileManager() {
		register(FileZipper.class);
		register(ZippingFileManager.class);
		registerInstance("uploadDirectory", new File(getProperty("java.io.tmpdir")+separator+"upload"));
	}


}
