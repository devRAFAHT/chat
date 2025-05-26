# 💻 Socket - Comunicação Cliente Servidor

## ✅ Requisitos

- **Java SDK 21** instalado

Verifique se o Java está instalado com o comando:

```bash
java -version
```

---

## 🚀 Como Executar

### 🔧 1. Compile os arquivos

Dentro da pasta raiz do projeto: `socket` siga os passos abaixo:

#### 🪟🪟 Windows:

```cmd
cd server\src
javac Main.java

cd ..\..\client\src
javac Main.java
```

#### 🐧 Linux/macOS:

```bash
cd server/src
javac Main.java

cd ../../client/src
javac Main.java
```

---

### ▶️ 2. Inicie o Servidor

> O servidor sempre deve ser iniciado **primeiro**.

#### 🪟🪟 Windows:

```cmd
cd server\src
java Main
```

#### 🐧 Linux/macOS:

```bash
cd server/src
java Main
```

---

### 💬 3. Inicie o Cliente

#### 🪟🪟 Windows:

```cmd
cd client\src
java Main
```

#### 🐧 Linux/macOS:

```bash
cd client/src
java Main
```

---

## 🌐 Acesso via Dispositivos na Mesma Rede

Para que outros dispositivos na mesma rede local consigam acessar o servidor, **o servidor e os clientes deveram usar o endereço IPv4**.

### 🔍 Como descobrir o Endereço IPv4:

#### 🪟🪟 Windows:

```cmd
ipconfig
```

#### 🐧 Linux/macOS:

```bash
ifconfig
```

ou

```bash
ip a
```
