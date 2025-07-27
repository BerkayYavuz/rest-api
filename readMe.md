#  Movie API (Spring Boot )

Bu proje bir film API'sidir. Spring Boot kullanılarak geliştirilmiştir ve JWT (Bearer Token) ile güvenlik sağlanmaktadır.

---------------------------

## Özellikler

-  JWT ile login/register işlemleri
-  CRUD (Create, Read, Update, Delete) işlemleri
-  Hangi kullanıcı filmi ekledi bilgisi (`createdBy`)
-  Film arama (query parametre ile)
-  JSON dosyasına da yedekleme (`movies.json`)

---------------------------

##  Gereksinimler

- Java 17+
- Maven (gerekmez, wrapper kullanılıyor)
- Herhangi bir IDE (IntelliJ önerilir) veya terminal

---------------------------

##  Projeyi Çalıştırma

### 1. Kopyalama


```bash
git clone https://github.com/kullaniciAdi/movie-api.git

cd movie-api
```

### 2. Linux/MacOS için Çalıştırma

```
./mwnw spring-boot:run 
```

### 3. Windows için Çalıştırma
```
.\mvnw.cmd spring-boot:run
```