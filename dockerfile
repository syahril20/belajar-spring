# Gunakan Alpine Linux kecil
FROM alpine:3.18

# Copy binary native
COPY target/demo /app/demo
WORKDIR /app

# Jalankan binary
CMD ["./demo"]
