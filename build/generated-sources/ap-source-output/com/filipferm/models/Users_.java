package com.filipferm.models;

import com.filipferm.models.ForumPosts;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-13T12:50:42")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, String> password;
    public static volatile SingularAttribute<Users, String> salt;
    public static volatile CollectionAttribute<Users, ForumPosts> forumPostsCollection;
    public static volatile SingularAttribute<Users, Integer> userId;
    public static volatile SingularAttribute<Users, String> email;
    public static volatile SingularAttribute<Users, Integer> iterations;
    public static volatile SingularAttribute<Users, String> username;

}