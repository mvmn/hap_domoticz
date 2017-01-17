package x.mvmn.hap.domoticz.devmanager.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import x.mvmn.hap.domoticz.devmanager.model.User;
import x.mvmn.hap.domoticz.devmanager.service.persistence.UserRepository;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	protected UserRepository userRepository;

	@RequestMapping(path = "/all")
	public List<User> listUsers() {
		return StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@RequestMapping(path = "/{userName}")
	public void deleteUser(@PathVariable("userName") String userName, HttpServletResponse response) {
		User user = userRepository.findByUsername(userName);
		if (user != null) {
			userRepository.delete(user);
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}
