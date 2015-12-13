package com.filipferm.models;

import com.filipferm.models.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-13T12:50:42")
@StaticMetamodel(ForumPosts.class)
public class ForumPosts_ { 

    public static volatile SingularAttribute<ForumPosts, Integer> idForumPosts;
    public static volatile SingularAttribute<ForumPosts, String> message;
    public static volatile SingularAttribute<ForumPosts, Users> userId;

}