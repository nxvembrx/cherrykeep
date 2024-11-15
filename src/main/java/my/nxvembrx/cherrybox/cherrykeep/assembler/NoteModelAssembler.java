package my.nxvembrx.cherrybox.cherrykeep.assembler;

import my.nxvembrx.cherrybox.cherrykeep.controller.NoteController;
import my.nxvembrx.cherrybox.cherrykeep.entity.Note;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class NoteModelAssembler implements RepresentationModelAssembler<Note, EntityModel<Note>> {
    @Override
    public EntityModel<Note> toModel(Note note) {
        EntityModel<Note> noteModel = EntityModel.of(note,
                // TODO: Maybe it's not the best way to do this, look deeper later
                linkTo(NoteController.class).slash("notes").slash(note.getId()).withSelfRel(),
                linkTo(methodOn(NoteController.class).deleteNote(note.getId())).withRel("delete"),
                linkTo(NoteController.class).slash("notes").slash(note.getId()).slash("edit").withRel("edit"),
                linkTo(NoteController.class).slash("users").slash(note.getUser().getId()).slash("notes").withRel("notes"));

        if (note.isPinned()) {
            noteModel.add(linkTo(methodOn(NoteController.class).unpin(note.getId())).withRel("pin"));
        } else {
            noteModel.add(linkTo(methodOn(NoteController.class).pin(note.getId())).withRel("pin"));
        }

        return noteModel;
    }
}
