package io.dumasoft.library.controller;

import io.dumasoft.library.models.dao.IAuthorDao;
import io.dumasoft.library.models.entity.Author;
import io.dumasoft.library.models.entity.Book;
import io.dumasoft.library.service.IAuthorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/author")
public class AuthorController {
    private IAuthorService authorService;

    public AuthorController(IAuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("authors", authorService.findAll());

        return "authors/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        Author author = new Author();
        model.addAttribute("author", author);
        return "authors/create";
    }

    @PostMapping("/create")
    public String save(
            @Valid Author author,
            BindingResult result,
            SessionStatus status,
            RedirectAttributes flash
    ) {

        if (result.hasErrors()) {
            flash.addFlashAttribute("error", "El autor no se pudo crear o actualizar");
            return "authors/create";
        }



        flash.addFlashAttribute("El autor se creó o actualizó correctamente");
        authorService.save(author);
        status.setComplete();
        return "redirect:/author/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(value = "id") Long id, Model model) {
        if (id <= 0) {
            return "redirect:/author/list";
        }

        Author author = authorService.findOne(id);

        model.addAttribute("author", author);

        return "authors/create";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            authorService.delete(id);
            flash.addFlashAttribute("success", "El autor ha sido eleminado correctamente");
        }

        return "redirect:/author/list";
    }
}
