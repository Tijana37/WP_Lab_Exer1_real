package mk.ukim.finki.wp.lab.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class TeacherFullnameConverter implements AttributeConverter<TeacherFullname, String> {

    private static final String SEPARATOR = ", ";

    @Override
    public String convertToDatabaseColumn(TeacherFullname teacherFullname) {
        if (teacherFullname == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        if (teacherFullname.getSurname()!= null && !teacherFullname.getSurname().isEmpty()) {
            sb.append(teacherFullname.getSurname());
            sb.append(SEPARATOR);
        }

        if (teacherFullname.getName() != null
                && !teacherFullname.getName().isEmpty()) {
            sb.append(teacherFullname.getName());
        }

        return sb.toString();
    }

    @Override
    public TeacherFullname convertToEntityAttribute(String dbTeacherFullname) {
        if (dbTeacherFullname == null || dbTeacherFullname.isEmpty()) {
            return null;
        }

        String[] pieces = dbTeacherFullname.split(SEPARATOR);

        if (pieces == null || pieces.length == 0) {
            return null;
        }

        TeacherFullname teacherFullname = new TeacherFullname();
        String firstPiece = !pieces[0].isEmpty() ? pieces[0] : null;
        if (dbTeacherFullname.contains(SEPARATOR)) {
            teacherFullname.setSurname(firstPiece);

            if (pieces.length >= 2 && pieces[1] != null
                    && !pieces[1].isEmpty()) {
                teacherFullname.setName(pieces[1]);
            }
        } else {
            teacherFullname.setName(firstPiece);
        }

        return teacherFullname;
    }
}
