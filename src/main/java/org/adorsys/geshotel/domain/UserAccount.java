package org.adorsys.geshotel.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.adorsys.geshotel.resto.domain.PeriodConf;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findUserAccountsByIdentifierEquals" })
public class UserAccount {

    private static Date accExp = DateUtils.addYears(new Date(), 50);

    @NotNull
    @Column(unique = true)
    @Size(min = 3)
    private String identifier;

    @NotNull
    @Column(unique = false)
    @Size(min = 5)
    private String password;

    @NotNull
    private String lastName;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private Date entryDate;

    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<RoleName> roleNames = new HashSet<RoleName>();

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date accountExpiration = accExp;

    @NotNull
    private boolean disableLogin;

    @NotNull
    private boolean accountLocked;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date credentialExpiration = accExp;

    @ManyToOne
    private PeriodConf periodConf;

    public List<RoleName> checkRoles(Set<RoleName> roleNames) {
        List<RoleName> result = new ArrayList<RoleName>();
        Set<RoleName> roleNamesFound = UserAccount.findUserAccount(getId()).getRoleNames();
        if (roleNames == null) throw new NullPointerException("The rolesName parameter might be null");
        for (RoleName roleName : roleNames) {
            for (RoleName roleFound : roleNamesFound) {
                if (roleFound.equals(roleName)) result.add(roleFound);
                break;
            }
        }
        if (result.isEmpty()) return null;
        return result;
    }

    public boolean hasRole(Set<RoleName> roleNames) {
        List<RoleName> checkRoles = checkRoles(roleNames);
        if (checkRoles != null) return true;
        return false;
    }

    @PrePersist
    public void prePersist() {
        this.password = encodePassword(password, getSalt());
    }

    public String encodePassword(String password, String salt) {
        if (StringUtils.isEmpty(password) || salt == null) throw new NullPointerException("Neither password nor salt may not be null");
        Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
        return md5PasswordEncoder.encodePassword(password, salt);
    }

    private final String getSalt() {
        return "ace6b4f53";
    }
    public boolean checkExistingPasword(String currentPassword) {
        String md5EncodedPassword = encodePassword(currentPassword);
        return StringUtils.equals(password, md5EncodedPassword);
    }
    private String encodePassword(String input) {
        Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
        md5PasswordEncoder.setEncodeHashAsBase64(false);
        return md5PasswordEncoder.encodePassword(input, getSalt());
    }
    public void changePassword(String password) {
        this.password = encodePassword(password);
    }
}
