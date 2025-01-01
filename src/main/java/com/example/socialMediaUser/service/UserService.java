package com.example.socialMediaUser.service;

import com.example.socialMediaUser.exception.UserNotFoundException;
import com.example.socialMediaUser.exception.InvalidUserException;
import com.example.socialMediaUser.model.User;
import com.example.socialMediaUser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Tüm postları listele
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ID'ye göre Post bul, kullanıcı yoksa hata fırlat
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException( id + " numaralı kullanıcı YOOOOK."));
    }

    // Post oluştur, geçersiz veri durumunda hata fırlat
    public User createUser(User user) {
        if (user.getEmail() == null || !user.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new InvalidUserException("Geçersiz e-posta. Lütfen geçerli bir e-posta giriniz");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new InvalidUserException("İsim alanı boş olamaz. Lütfen isim giriniz");
        }
        return userRepository.save(user);
    }

    // Post sil, post yoksa hata fırlat
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException( id + " numaralı kullanıcı bulunamadı.");
        }
        userRepository.deleteById(id);
    }
}
