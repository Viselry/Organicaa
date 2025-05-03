# Dùng base image cho Java
FROM openjdk:11-jre-slim

# Tạo thư mục trong container để chứa ứng dụng
WORKDIR /app

# Copy file JAR từ thư mục hiện tại vào container
COPY ./target/organica.jar /app/organica.jar

# Cấu hình entrypoint để chạy ứng dụng khi container khởi động
ENTRYPOINT ["java", "-jar", "organica.jar"]
