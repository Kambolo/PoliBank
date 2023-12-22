from email.message import EmailMessage
import ssl  #connection secure
import smtplib
import sys

email_sender = "polibankpk@gmail.com"
email_password = "krzw hmun kzai cyeo"
email_reciver = sys.argv[1]


subject = "Przypomnienie hasła"
body = "Hasło do twojego konta w aplikacji PoliBank\nHasło: {}".format(sys.argv[2])

em = EmailMessage()

em['From'] = email_sender
em["To"] = email_reciver
em['Subject'] = subject
em.set_content(body)

context = ssl.create_default_context()

with smtplib.SMTP_SSL('smtp.gmail.com', 465, context=context) as smtp:
        smtp.login(email_sender, email_password)
        smtp.sendmail(email_sender, email_reciver, em.as_string())

