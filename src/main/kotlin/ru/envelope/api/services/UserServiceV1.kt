package ru.envelope.api.services

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.envelope.api.dto.user.UserDto
import ru.envelope.api.dto.user.UserPutDto
import ru.envelope.api.entities.User
import ru.envelope.api.exceptions.UserNotFoundException
import ru.envelope.api.mappers.UserMapper
import ru.envelope.api.mappers.UsersProjectionMapper
import ru.envelope.api.repositories.UserRepository

@Service
class UserServiceV1(
    private val userRepository: UserRepository
): UserService {
    override fun findById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

    override fun getUsers(pageNumber: Int, pageSize: Int, sortField: String, sortOrder: Sort.Direction): List<UserDto> {
        val pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(sortOrder, sortField))
        return userRepository.findAllWithProjection(pageRequest)
            .groupBy { it.getId() }
            .values
            // из-за того что .values возвращает список, а не стрим
            .map(UsersProjectionMapper::apply)
            .toList()
    }

    override fun createUser(id: Long, firstName: String, lastName: String?, username: String?): User {
        return userRepository.save(User(
            id = id,
            fullName = firstName + if (lastName != null) " $lastName" else "",
            username = username,
            verified = false))
    }

    override fun updateUser(id: Long, userDto: UserPutDto): UserDto {
        val userEntity = userRepository.findById(id)

        if (userEntity.isEmpty) {
            throw UserNotFoundException(id)
        }

        val user = userEntity.get()
        if (userDto.verified != null) {
            user.verified = userDto.verified
        }
        if (userDto.roles != null) {
            user.authorities = userDto.roles.toMutableSet()
        }

        return UserMapper.apply(userRepository.save(user))
    }
}
