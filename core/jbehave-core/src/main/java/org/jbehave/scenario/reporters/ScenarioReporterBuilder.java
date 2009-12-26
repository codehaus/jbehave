package org.jbehave.scenario.reporters;

import java.util.HashMap;
import java.util.Map;

import org.jbehave.scenario.reporters.FilePrintStreamFactory.FileConfiguration;

/**
 * <p>
 * A <a href="http://en.wikipedia.org/wiki/Builder_pattern">Builder</a> for
 * {@link ScenarioReporter}s. It builds a {@link DelegatingScenarioReporter}
 * with delegates for a number of formats - mostly file-based ones except
 * {@Format.CONSOLE}. It requires a
 * {@link FilePrintStreamFactory} and provides default delegate instances for
 * each format.
 * </p>
 * <p>
 * To build reporter with default delegates for given formats:
 * 
 * <pre>
 * Class&lt;MyScenario&gt; scenarioClass = MyScenario.class;
 * ScenarioNameResolver nameResolver = new UnderscoredCamelCaseResolver();
 * FilePrintStreamFactory printStreamFactory = new FilePrintStreamFactory(scenarioClass, nameResolver);
 * ScenarioReporter reporter = new ScenarioReporterBuilder(printStreamFactory).with(HTML).with(TXT).with(STATS).build();
 * </pre> 
 * </p>
 * <p>To override the default instance of a given reporter delegate, e.g. to report format <b>TXT</b> to <b>.text</b> files
 * and to inject other non-default parameters, such as keywords for a different locale:
 * <pre>
 * ScenarioReporter reporter = new ScenarioReporterBuilder(printStreamFactory){
 *   public ScenarioReporter reporterFor(Format format){
 *       switch (format) {
 *           case TXT:
 *               factory.useConfiguration(new FileConfiguration("text"));
 *               return new PrintStreamScenarioReporter(factory.getPrintStream(), new Properties(), new I18nKeywords(Locale), true);
 *            default:
 *               return super.reporterFor(format);
 *   }
 * }
 * </pre>
 */
public class ScenarioReporterBuilder {

    public enum Format {
        CONSOLE, STATS, TXT, HTML, XML
    };

    protected final FilePrintStreamFactory factory;
    protected Map<Format, ScenarioReporter> delegates = new HashMap<Format, ScenarioReporter>();

    public ScenarioReporterBuilder(FilePrintStreamFactory factory) {
        this.factory = factory;
    }

    public ScenarioReporter build() {
        return new DelegatingScenarioReporter(delegates.values());
    }

    public ScenarioReporterBuilder with(Format format) {
        delegates.put(format, reporterFor(format));
        return this;
    }

    public ScenarioReporter reporterFor(Format format) {
        switch (format) {
            case CONSOLE:
                return new PrintStreamScenarioReporter();
            case STATS:
                factory.useConfiguration(new FileConfiguration("stats"));
                return new StatisticsScenarioReporter(factory.getPrintStream());
            case TXT:
                factory.useConfiguration(new FileConfiguration("txt"));
                return new PrintStreamScenarioReporter(factory.getPrintStream());
            case HTML:
                factory.useConfiguration(new FileConfiguration("html"));
                return new HtmlPrintStreamScenarioReporter(factory.getPrintStream());
            case XML:
                factory.useConfiguration(new FileConfiguration("xml"));
                return new XmlPrintStreamScenarioReporter(factory.getPrintStream());
            default:
                throw new UnsupportedReporterFormatException(format);
        }
    }

    @SuppressWarnings("serial")
    public static class UnsupportedReporterFormatException extends RuntimeException {

        public UnsupportedReporterFormatException(Format format) {
            super("Building ScenarioReporter not supported for format " + format);
        }

    }

}
