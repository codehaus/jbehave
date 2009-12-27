
cd target 

cp -r jbehave-reports/rendered jbehave-trader-reports

zip -r jbehave-trader-reports.zip jbehave-trader-reports

scp jbehave-trader-reports.zip jbehave.org:

ssh jbehave.org "rm -r jbehave-trader-reports; unzip jbehave-trader-reports.zip; mv jbehave-trader-reports /var/www/jbehave.org/reference/latest/"
