# vulnapp

Do not run this app on a network-facing system!! The included servlet outputs every parameter name/value and every header name/value, and then logs it with a vulnerable version of log4j. It is very vulnerable to XSS, Log4Shell, and who knows what else. The purpose is to evaluate Log4Shell scanner tools.