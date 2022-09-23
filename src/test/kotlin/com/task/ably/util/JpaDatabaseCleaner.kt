package com.task.ably.util

import com.google.common.base.CaseFormat.LOWER_UNDERSCORE
import com.google.common.base.CaseFormat.UPPER_CAMEL
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import javax.persistence.Entity
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional
import kotlin.reflect.full.findAnnotation

@Service
class JpaDatabaseCleaner : InitializingBean {

    @PersistenceContext
    lateinit var em: EntityManager

    private lateinit var tables: List<String>

    override fun afterPropertiesSet() {
        tables = em.metamodel.entities
            .filter {
                it.javaType.kotlin.findAnnotation<Entity>() != null
            }.map {
                UPPER_CAMEL.to(LOWER_UNDERSCORE, it.name)
            }
    }

    @Transactional
    fun truncate() {
        em.flush()
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()
        tables.forEach { name ->
            em.createNativeQuery("TRUNCATE TABLE $name").executeUpdate()
        }
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
    }
}
