To deploy:

- Using Maven with embedded Jetty

mvn jetty:run-war

- Using Maven with a webapp container (e.g. Jetty, Tomcat, etc ...)

mvn clean install
Copy target/jbehave-trader.war to container deploy dir and start container.

- If prefer not using Maven to build the war, then you can use the snapshot deployed on 

http://snapshots.repository.codehaus.org/org/jbehave/web/jbehave-trader-webapp/2.0-SNAPSHOT/

To use: 

Open webapp page at

http://localhost:8080/jbehave-trader (assuming normal port conventions)

Click on Run Scenario link in navigation menu.

Input textual scenario in textarea and click on Run Scenario link.

An example of trader scenario to input (you can cut & paste) can be found in src/main/resources.

Note: src/main/webapp only contains the custom resources that override the default ones contained in 
jbehave-web-waffle.  So, e.g. to change override default style, only src/main/webapp/style/jbehave.css needs
to be added. Similary, to change home page template, only src/main/webapp/ftl/home.ftl is needed.  If any of 
these are removed the defaults will apply.

