package bg.galaxi.voter.model.entity;

import bg.galaxi.voter.model.enumeration.ActionMade;
import bg.galaxi.voter.model.enumeration.AffectedTable;
import bg.galaxi.voter.model.enumeration.RoleName;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

import static bg.galaxi.voter.util.AppConstants.LOGGER;
import static bg.galaxi.voter.util.AppConstants.ROLE_NAME_LENGH_VALUE;

@Entity
@Table(name = LOGGER)
public class Logger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = ROLE_NAME_LENGH_VALUE)
    private AffectedTable table;

    @Enumerated(EnumType.STRING)
    @Column(length = ROLE_NAME_LENGH_VALUE)
    private ActionMade action;

    public Logger() {
    }

    public Logger(AffectedTable table, ActionMade action) {
        this.table = table;
        this.action = action;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AffectedTable getTable() {
        return table;
    }

    public void setTable(AffectedTable table) {
        this.table = table;
    }

    public ActionMade getAction() {
        return action;
    }

    public void setAction(ActionMade action) {
        this.action = action;
    }
}
