package com.asif.student.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.asif.student.model.Student;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	ApplicationContext context = new ClassPathXmlApplicationContext(
			"classpath:Beans.xml");

	StudentRepository studentRepository = (StudentRepository) context
			.getBean("studentRepository");

	@ModelAttribute("allStudents")
	public List<Student> allStudents() {
		return this.studentRepository.findAllStudents();
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Student student) {
		return "home";
	}

	@RequestMapping(value = "/student.html", params = { "save" })
	public String saveSeedstarter(final Student student,
			final BindingResult bindingResult, final ModelMap model) {
		if (bindingResult.hasErrors()) {
			return "home";
		}
		this.studentRepository.studentsList.add(student);
		model.clear();
		return "redirect:/";
	}

	@RequestMapping(value = "/student.html", params = { "editRow" })
	public String removeRow(final Student student,
			final HttpServletRequest req, Model model) {
		int rowId = Integer.valueOf(req.getParameter("editRow")).intValue();
		Student s = this.studentRepository.getStudent(rowId);
		student.setStudentId(s.getStudentId());
		student.setStudentName(s.getStudentName());
		student.setStudentEmail(s.getStudentEmail());
		model.addAttribute("token", rowId);

		return "edit";
	}

	@RequestMapping(value = "/student.html", params = { "update" })
	public String updateStd(final Student student, final HttpServletRequest req) {
		int listIndex = Integer.valueOf(req.getParameter("update")).intValue();
		this.studentRepository.studentsList.set(listIndex, student);
		return "redirect:/";
	}

	@RequestMapping(value = "/student.html", params = { "removeRow" })
	public String removeStd(final HttpServletRequest req) {
		int listIndex = Integer.valueOf(req.getParameter("removeRow"))
				.intValue();
		this.studentRepository.studentsList.remove(listIndex);
		return "redirect:/";
	}

}
