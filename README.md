**Master's thesis**: "Multimodal machine learning system for medical application"

The main goal of the master's thesis was to design and build a system which could support doctors in diagnosing diseases.
The system consists of several parts in different repositories:
- main server written using Scala, Play Framework, Slick, HTML, CSS, jQuery (current repository)
- additional services such as: proxy-server, custom scikit server (breast-cancer-server), tensorflow/serving (skin-lesions-server), BCrypt hashing 
written using Python, Flask, SQLAlchemy, Scala
(https://github.com/imarkiew/Multimodal-ML-System-Services)
- models: skin lesion classification with the usage of keras & tensorflow, breast cancer classification with usage of scikit & xgboost (https://github.com/imarkiew/Multimodal-ML-System-Models)

Most services are used together with Docker and Docker Compose. MySQL 8.x was used as database.

<p align="center">
<img src="./readme-content/systemSchema.png" width="75%" height="75%">
</p>
The green color is Docker Compose and the blue is instance that serves the system (Google Cloud Engine was used)

<p align="center">
<img src="./readme-content/databaseUML.png" width="75%" height="75%">
</p>

<p align="center">
<img src="./readme-content/frontend1.png" width="85%" height="85%">
</p>

<p align="center">
<img src="./readme-content/frontend3.png" width="85%" height="85%">
</p>

<p align="center">
<img src="./readme-content/frontend4.png" width="85%" height="85%">
</p>
