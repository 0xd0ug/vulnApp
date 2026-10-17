# Installation and Setup Guide for Mac OS

This guide walks through setting up and running **vulnApp** locally using Apple's native container tool and Apache Tomcat.

> [!CAUTION]
> **Security Notice:** This application contains intentional vulnerabilities (Log4Shell CVE-2021-44228, reflected XSS, etc.). Always bind network ports strictly to `127.0.0.1` (localhost) and never expose this container to a public or shared network.

---

## 1. Install Apple Container

Apple's native container platform for macOS can be installed using the pre-built `.pkg` installer from GitHub.

1. Navigate to the repository releases:
   * **Repository:** [https://github.com/apple/container](https://github.com/apple/container)
   * **Releases:** [https://github.com/apple/container/releases](https://github.com/apple/container/releases)
2. Download the latest release `.pkg` file (e.g., `container-<version>.pkg`).
3. Run the installer:
   * Double-click the downloaded `.pkg` file to install via the macOS installer.
4. Verify the installation:
   ```bash
   container --version
   ```

---

## 2. Build the Application

Use the included Maven wrapper (`mvnw`) to compile and package the web application.

```bash
./mvnw clean package
```

Upon a successful build, the following build artifacts will be generated in `target/`:
* `target/vulnApp-1.0-SNAPSHOT` (exploded WAR directory)
* `target/vulnApp-1.0-SNAPSHOT.war` (packaged WAR archive)

---

## 3. Run in Tomcat Using Apple Container

Because `vulnApp` uses the `javax.servlet` API (Java EE 8), run it inside a **Tomcat 9** container with Java 8.

### Step 3.1: Start the Tomcat 9 Container
Start a detached container and bind port `8080` strictly to `127.0.0.1` (localhost):

```bash
container run -d --name tomcat9 -p 127.0.0.1:8080:8080 tomcat:9-jdk8
```

### Step 3.2: Deploy the Application
Copy the exploded application directory into Tomcat's `webapps/ROOT` directory:

```bash
container cp target/vulnApp-1.0-SNAPSHOT tomcat9:/usr/local/tomcat/webapps/ROOT
```

*(Alternatively, you can copy the `.war` archive: `container cp target/vulnApp-1.0-SNAPSHOT.war tomcat9:/usr/local/tomcat/webapps/ROOT.war`)*

---

## 4. Verify Deployment

Test that the servlet is accessible:

```bash
curl -i "http://127.0.0.1:8080/hello-servlet?status=ready"
```

You should receive an HTTP 200 response displaying the parameter and the Java/Log4j version information:
```text
Parameter count: 1
status:ready
...
Java version 1.8.0_...
Log4j version 2.13.0
```

---

## 5. Container Management Commands

* **View running containers:**
  ```bash
  container list
  ```
* **View container logs:**
  ```bash
  container logs tomcat9
  ```
* **Stop the container:**
  ```bash
  container stop tomcat9
  ```
* **Restart the container:**
  ```bash
  container start tomcat9
  ```
* **Remove the container:**
  ```bash
  container stop tomcat9 && container rm tomcat9
  ```
