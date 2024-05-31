package com.Restaurant.application.service;

import com.Restaurant.application.api.UserAPI.authenticateUserAPI;
import com.Restaurant.application.auth.AuthenticationRequest;
import com.Restaurant.application.auth.AuthenticationResponse;
import com.Restaurant.application.auth.RegisterRequest;
import com.Restaurant.application.config.JwtService;
import com.Restaurant.application.config.JwtTokenHolder;
import com.Restaurant.application.dto.SearchRequest;
import com.Restaurant.application.dto.UserDto;
import com.Restaurant.application.entity.User;
import com.Restaurant.application.exception.exceptions.emailTakenException;
import com.Restaurant.application.exception.exceptions.entityNotExistException;
import com.Restaurant.application.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service @RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final EntityManager em;

    public UserDto register(RegisterRequest request) {

        Boolean taken = repository.existsByEmail(request.getEmail());
        if (taken) {
            throw new emailTakenException("Email already taken.");
        }
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .phoneNumber(request.getPhoneNumber())
                .build();

        repository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public UserDto deleteUser(Long id)
    {
        boolean exists = repository.existsById(id);
        if (!exists) {
            throw new entityNotExistException("User not found");
        }

        UserDto deleted = modelMapper.map(repository.findById(id),UserDto.class);
        repository.deleteById(id);
        return deleted;
    }

    public UserDto getUser() {
        String token = JwtTokenHolder.getToken();
        String email = jwtService.extractUsername(token);
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return modelMapper.map(user, UserDto.class);
    }

    @Transactional
    public UserDto updateUser(String firstname, String lastname, String newEmail, String password,String phoneNumber)
    {
        String token = JwtTokenHolder.getToken();
        String email = jwtService.extractUsername(token);
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        if(firstname!=null &&
                firstname.length()>0 &&
                !Objects.equals(user.getFirstname(),firstname))
            user.setFirstname(firstname);

        if(lastname!=null &&
                lastname.length()>0 &&
                !Objects.equals(user.getLastname(),lastname))
            user.setLastname(lastname);

        if(password!=null &&
                password.length()>0 &&
                !Objects.equals(user.getPassword(),password))
            user.setPassword(password);

        if(phoneNumber!=null &&
                phoneNumber.length()>0 &&
                !Objects.equals(user.getPhoneNumber(),phoneNumber))
            user.setPhoneNumber(phoneNumber);

        if(newEmail!=null && newEmail.length()>0 && !Objects.equals(user.getEmail(),newEmail)) {
            Boolean taken = repository.existsByEmail(newEmail);
            if (taken) {
                throw new emailTakenException("Email already taken.");
            }
            user.setEmail(newEmail);
        }
        repository.save(user);
        return modelMapper.map(user, UserDto.class);
    }


    public boolean isUserTableEmpty()
    {
        return repository.count() == 0;
    }

    public List<User> getAllUsers() {
        if(isUserTableEmpty())
            throw new entityNotExistException("User table is empty");
        return repository.findAll();
    }

    public List<User> findAllByCriteria(SearchRequest request){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        List<Predicate> predicates = new ArrayList<>();

        // select from user
        Root<User> root = criteriaQuery.from(User.class);

        if(request.getFirstname()!=null){
            Predicate firstnamePredicate = criteriaBuilder
                    .like(root.get("firstname"),"%"+request.getFirstname()+"%");
            predicates.add(firstnamePredicate);
        }

        if(request.getLastname()!=null){
            Predicate lastnamePredicate = criteriaBuilder
                    .like(root.get("lastname"),"%"+request.getLastname()+"%");
            predicates.add(lastnamePredicate);
        }

        if(request.getEmail()!=null){
            Predicate emailPredicate = criteriaBuilder
                    .like(root.get("email"),"%"+request.getEmail()+"%");
            predicates.add(emailPredicate);
        }

        criteriaQuery.where(
                criteriaBuilder.or(predicates.toArray(new Predicate[0]))
        );

        TypedQuery<User> query = em.createQuery(criteriaQuery);
        List<User> result =  query.getResultList();
        if(result.isEmpty())
            throw new entityNotExistException("Users not found");
        else
            return result;
    }






}






