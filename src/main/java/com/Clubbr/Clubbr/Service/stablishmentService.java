package com.Clubbr.Clubbr.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.Clubbr.Clubbr.Entity.*;
import com.Clubbr.Clubbr.advice.*;
import com.Clubbr.Clubbr.utils.role;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Clubbr.Clubbr.Repository.stablishmentRepo;
import org.springframework.transaction.annotation.Transactional;
import com.Clubbr.Clubbr.Repository.managerRepo;
import com.Clubbr.Clubbr.Repository.userRepo;
import com.Clubbr.Clubbr.Repository.workerRepo;
import com.Clubbr.Clubbr.Repository.interestPointRepo;
import com.Clubbr.Clubbr.Repository.eventRepo;

/**
 * Servicio para gestionar los establecimientos.
 */
@Service
public class stablishmentService {

    @Autowired
    private stablishmentRepo stabRepo;

    @Autowired
    private jwtService jwtService;

    @Autowired
    private managerRepo managerRepo;

    @Autowired
    private workerRepo workerRepo;

    @Autowired
    private userRepo userRepo;

    @Autowired
    private interestPointRepo interestPointRepo;

    @Autowired
    private eventRepo eventRepo;

    /**
     * Obtiene todos los establecimientos.
     * @return una lista de todos los establecimientos.
     */
    @Transactional(readOnly = true)
    public List<stablishment> getAllStab() {
        return stabRepo.findAll();
    }

    /**
     * Obtiene un establecimiento por su ID.
     * @param stabID el ID del establecimiento.
     * @return el establecimiento con el ID proporcionado.
     * @throws StablishmentNotFoundException si no se encuentra el establecimiento.
     */
    public stablishment getStab(Long stabID) {
        return stabRepo.findById(stabID)
                .orElseThrow(() -> new StablishmentNotFoundException("No se ha encontrado el establecimiento con el ID " + stabID));
    }

    /**
     * Obtiene todos los establecimientos de un manager.
     * @param token el token del manager.
     * @return una lista de todos los establecimientos del manager.
     * @throws UserNotFoundException si no se encuentra el usuario.
     * @throws ManagerNotFoundException si no se encuentra el manager.
     * @throws StablishmentNotFoundException si no se encuentran establecimientos.
     */
    public List<stablishment> getAllStablishmentFromManager(String token) {
        String userId = jwtService.extractUserIDFromToken(token);

        user user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No se ha encontrado el usuario con el ID " + userId));

        manager stabManager = managerRepo.findByUserID(user)
                .orElseThrow(() -> new ManagerNotFoundException("No se ha encontrado el manager con el ID " + userId));

        List<stablishment> stablishments = stabRepo.findByManagerID(stabManager);
        if (stablishments.isEmpty()) {
            throw new StablishmentNotFoundException("No se han encontrado establecimientos asociados al manager con el ID " + userId);
        }
        return stablishments;
    }

    /**
     * Elimina un establecimiento.
     * @param stabID el ID del establecimiento.
     * @param token el token del manager.
     * @throws UserNotFoundException si no se encuentra el usuario.
     * @throws ManagerNotFoundException si no se encuentra el manager.
     * @throws StablishmentNotFoundException si no se encuentra el establecimiento.
     * @throws ManagerNotFromStablishmentException si el manager no pertenece al establecimiento.
     */
    public void deleteStab(Long stabID, String token) {
        String userId = jwtService.extractUserIDFromToken(token);

        user targetUser = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No se ha encontrado el usuario con el ID " + userId));


        stablishment targetStab = stabRepo.findById(stabID)
                .orElseThrow(() -> new StablishmentNotFoundException("No se ha encontrado el establecimiento con el ID " + stabID));

        if (targetUser.getUserRole() != role.ADMIN) {
            manager manager = managerRepo.findByUserID(targetUser).orElse(null);
            if (manager == null) {
                throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + targetUser.getUserID());
            }

            if (!isManagerInStab(targetStab, manager)) {
                throw new ManagerNotFromStablishmentException("El manager con el ID " + targetUser.getUserID() + " no es manager del establecimiento con el ID " + targetStab.getStablishmentID());
            }
        }


        stabRepo.deleteById(stabID);
    }

    /**
     * Añade un establecimiento.
     * @param newStab el nuevo establecimiento.
     * @param token el token del manager.
     * @throws UserNotFoundException si no se encuentra el usuario.
     * @throws ManagerNotFoundException si no se encuentra el manager.
     * @throws ManagerNotOwnerException si el manager no es el propietario.
     */
    public void addStablishment(stablishment newStab, String token) {
        String userId = jwtService.extractUserIDFromToken(token);

        user user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No se ha encontrado el usuario con el ID " + userId));

        if (user.getUserRole() == role.ADMIN){
            newStab.setManagerID(new ArrayList<>());
            stabRepo.save(newStab);
        }
    }

    /**
     * Añade un trabajador a un establecimiento.
     * @param stablishmentID el ID del establecimiento.
     * @param targetWorker el trabajador a añadir.
     * @param token el token del manager.
     * @throws StablishmentNotFoundException si no se encuentra el establecimiento.
     * @throws ManagerNotFoundException si no se encuentra el manager.
     * @throws UserNotFoundException si no se encuentra el usuario.
     * @throws ManagerNotFromStablishmentException si el manager no pertenece al establecimiento.
     */
    public void addWorkerToStab(Long stablishmentID, worker targetWorker, String token){
        String userId = jwtService.extractUserIDFromToken(token);

        stablishment targetStab = stabRepo.findById(stablishmentID)
            .orElseThrow(() -> new StablishmentNotFoundException("No se ha encontrado el establecimiento con el ID " + stablishmentID));

        user targetUser = userRepo.findById(targetWorker.getUserID().getUserID())
            .orElseThrow(() -> new UserNotFoundException("No se ha encontrado el usuario con el ID " + targetWorker.getUserID().getUserID()));

        if (targetUser.getUserRole() != role.ADMIN) {
            manager manager = managerRepo.findByUserID(targetUser).orElse(null);
            if (manager == null) {
                throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + targetUser.getUserID());
            }

            if (!isManagerInStab(targetStab, manager)) {
                throw new ManagerNotFromStablishmentException("El manager con el ID " + targetUser.getUserID() + " no es manager del establecimiento con el ID " + targetStab.getStablishmentID());
            }
        }

        targetUser.setUserRole(role.WORKER);
        targetWorker.setStablishmentID(targetStab);
        targetWorker.setWorkingHours(160L);
        targetStab.getWorkers().add(targetWorker);

        workerRepo.save(targetWorker);
        userRepo.save(targetUser);
        stabRepo.save(targetStab);
    }

    /**
     * Añade un trabajador a un punto de interés de un establecimiento.
     * @param stablishmentID el ID del establecimiento.
     * @param userID el ID del usuario que se va a añadir como trabajador.
     * @param interestPointID el ID del punto de interés al que se va a añadir el trabajador.
     * @param token el token del manager.
     * @throws StablishmentNotFoundException si no se encuentra el establecimiento.
     * @throws UserNotFoundException si no se encuentra el usuario.
     * @throws ManagerNotFoundException si no se encuentra el manager.
     * @throws WorkerNotFoundException si no se encuentra el trabajador.
     * @throws InterestPointNotFoundException si no se encuentra el punto de interés.
     * @throws ManagerNotFromStablishmentException si el manager no pertenece al establecimiento.
     * @throws InterestPointNotFromStablishmentException si el punto de interés no pertenece al establecimiento.
     * @throws WorkerNotFromStablishmentException si el trabajador no pertenece al establecimiento.
     */
    public void addWorkerToStabInterestPoint(Long stablishmentID, String userID, Long interestPointID, String token){
        String userId = jwtService.extractUserIDFromToken(token);

        stablishment targetStab = stabRepo.findById(stablishmentID)
            .orElseThrow(() -> new StablishmentNotFoundException("No se ha encontrado el establecimiento con el ID " + stablishmentID));

        user targetUser = userRepo.findById(userID)
            .orElseThrow(() -> new UserNotFoundException("No se ha encontrado el usuario con el ID " + userID));

        user requestUser = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("No se ha encontrado el usuario con el ID " + userId));

        worker worker = workerRepo.findByUserIDAndStablishmentID(targetUser, targetStab)
            .orElseThrow(() -> new WorkerNotFoundException("No se ha encontrado el trabajador con el ID " + userID + " en el establecimiento con el ID " + stablishmentID));

        interestPoint interestPoint = interestPointRepo.findById(interestPointID)
            .orElseThrow(() -> new InterestPointNotFoundException("No se ha encontrado el punto de interés con el ID " + interestPointID));

        if (requestUser.getUserRole() != role.ADMIN) {
            manager manager = managerRepo.findByUserID(requestUser).orElse(null);
            if (manager == null) {
                throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + requestUser.getUserID());
            }

            if (!isManagerInStab(targetStab, manager)) {
                throw new ManagerNotFromStablishmentException("El manager con el ID " + requestUser.getUserID() + " no es manager del establecimiento con el ID " + targetStab.getStablishmentID());
            }
        }

        if (interestPoint.getStablishmentID() != targetStab){
            throw new InterestPointNotFromStablishmentException("El punto de interés con el ID " + interestPointID + " no pertenece al establecimiento con el ID " + stablishmentID);
        }

        if (worker.getStablishmentID() != targetStab){
            throw new WorkerNotFromStablishmentException("El trabajador con el ID " + userID + " no pertenece al establecimiento con el ID " + stablishmentID);
        }

        worker.setInterestPointID(interestPoint);
        interestPoint.getWorkers().add(worker);
        interestPointRepo.save(interestPoint);
        workerRepo.save(worker);
    }

    /**
     * Añade un trabajador a un punto de interés de un evento en un establecimiento.
     * @param stablishmentID el ID del establecimiento.
     * @param eventName el nombre del evento.
     * @param userID el ID del usuario que se va a añadir como trabajador.
     * @param interestPointID el ID del punto de interés al que se va a añadir el trabajador.
     * @param token el token del manager.
     * @throws StablishmentNotFoundException si no se encuentra el establecimiento.
     * @throws UserNotFoundException si no se encuentra el usuario.
     * @throws ManagerNotFoundException si no se encuentra el manager.
     * @throws WorkerNotFoundException si no se encuentra el trabajador.
     * @throws EventNotFoundException si no se encuentra el evento.
     * @throws InterestPointNotFoundException si no se encuentra el punto de interés.
     * @throws WorkerNotFromStablishmentException si el trabajador no pertenece al establecimiento.
     * @throws ManagerNotFromStablishmentException si el manager no pertenece al establecimiento.
     * @throws InterestPointNotFromEventException si el punto de interés no pertenece al evento.
     */
    public void addWorkerToEventInterestPoint(Long stablishmentID, String eventName, String userID, Long interestPointID, String token){
        String userId = jwtService.extractUserIDFromToken(token);

        stablishment targetStab = stabRepo.findById(stablishmentID)
            .orElseThrow(() -> new StablishmentNotFoundException("No se ha encontrado el establecimiento con el ID " + stablishmentID));

        user targetUser = userRepo.findById(userID)
            .orElseThrow(() -> new UserNotFoundException("No se ha encontrado el usuario con el ID " + userID));

        user requestUser = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("No se ha encontrado el usuario con el ID " + userId));

        worker worker = workerRepo.findByUserIDAndStablishmentID(targetUser, targetStab)
            .orElseThrow(() -> new WorkerNotFoundException("No se ha encontrado el trabajador con el ID " + userID + " en el establecimiento con el ID " + stablishmentID));

        event event = eventRepo.findByEventNameAndStablishmentID(eventName, targetStab);
        if (event == null) {
            throw new EventNotFoundException("No se ha encontrado el evento con el nombre " + eventName + " en el establecimiento con el ID " + stablishmentID);
        }

        interestPoint interestPoint = interestPointRepo.findById(interestPointID)
            .orElseThrow(() -> new InterestPointNotFoundException("No se ha encontrado el punto de interés con el ID " + interestPointID));

        if (worker.getStablishmentID() != targetStab){
            throw new WorkerNotFromStablishmentException("El trabajador con el ID " + userID + " no pertenece al establecimiento con el ID " + stablishmentID);
        }

        if (requestUser.getUserRole() != role.ADMIN) {
            manager manager = managerRepo.findByUserID(requestUser).orElse(null);
            if (manager == null) {
                throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + requestUser.getUserID());
            }

            if (!isManagerInStab(targetStab, manager)) {
                throw new ManagerNotFromStablishmentException("El manager con el ID " + requestUser.getUserID() + " no es manager del establecimiento con el ID " + targetStab.getStablishmentID());
            }
        }

        if (interestPoint.getEventName() != event) {
            throw new InterestPointNotFromEventException("El punto de interés con el ID " + interestPointID + " no pertenece al evento con el nombre " + eventName);
        }

        worker.setInterestPointID(interestPoint);
        interestPoint.getWorkers().add(worker);
        interestPointRepo.save(interestPoint);
        workerRepo.save(worker);
    }

    /**
     * Elimina un trabajador de un establecimiento.
     * @param stablishmentID el ID del establecimiento.
     * @param userID el ID del usuario que se va a eliminar como trabajador.
     * @param token el token del manager.
     * @throws StablishmentNotFoundException si no se encuentra el establecimiento.
     * @throws UserNotFoundException si no se encuentra el usuario.
     * @throws ManagerNotFoundException si no se encuentra el manager.
     * @throws WorkerNotFoundException si no se encuentra el trabajador.
     * @throws WorkerNotFromStablishmentException si el trabajador no pertenece al establecimiento.
     * @throws ManagerNotFromStablishmentException si el manager no pertenece al establecimiento.
     */
    public void deleteWorkerFromStab(Long stablishmentID, String userID, String token){
        String userId = jwtService.extractUserIDFromToken(token);

        stablishment targetStab = stabRepo.findById(stablishmentID)
            .orElseThrow(() -> new StablishmentNotFoundException("No se ha encontrado el establecimiento con el ID " + stablishmentID));

        user targetUser = userRepo.findById(userID)
            .orElseThrow(() -> new UserNotFoundException("No se ha encontrado el usuario con el ID " + userID));

        user requestUser = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("No se ha encontrado el usuario con el ID " + userId));

        worker worker = workerRepo.findByUserIDAndStablishmentID(targetUser, targetStab)
            .orElseThrow(() -> new WorkerNotFoundException("No se ha encontrado el trabajador con el ID " + userID + " en el establecimiento con el ID " + stablishmentID));


        if (worker.getStablishmentID() != targetStab){
            throw new WorkerNotFromStablishmentException("El trabajador con el ID " + userID + " no pertenece al establecimiento con el ID " + stablishmentID);
        }

        if (requestUser.getUserRole() != role.ADMIN) {
            manager manager = managerRepo.findByUserID(requestUser).orElse(null);
            if (manager == null) {
                throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + requestUser.getUserID());
            }

            if (!isManagerInStab(targetStab, manager)) {
                throw new ManagerNotFromStablishmentException("El manager con el ID " + requestUser.getUserID() + " no es manager del establecimiento con el ID " + targetStab.getStablishmentID());
            }
        }

        targetUser.setUserRole(role.USER);
        targetStab.getWorkers().remove(worker);
        userRepo.save(targetUser);
        workerRepo.delete(worker);
        stabRepo.save(targetStab);
    }

    /**
     * Actualiza un establecimiento.
     * @param targetStab el establecimiento que se va a actualizar.
     * @param token el token del manager.
     * @throws StablishmentNotFoundException si no se encuentra el establecimiento.
     * @throws UserNotFoundException si no se encuentra el usuario.
     * @throws ManagerNotFoundException si no se encuentra el manager.
     * @throws ManagerNotFromStablishmentException si el manager no pertenece al establecimiento.
     */
    public void updateStab(stablishment targetStab, String token) {
        String userId = jwtService.extractUserIDFromToken(token);

        stablishment stablishment = stabRepo.findById(targetStab.getStablishmentID())
            .orElseThrow(() -> new StablishmentNotFoundException("No se ha encontrado el establecimiento con el ID " + targetStab.getStablishmentID()));

        user user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("No se ha encontrado el usuario con el ID " + userId));

        if (user.getUserRole() != role.ADMIN) {
            manager manager = managerRepo.findByUserID(user).orElse(null);
            if (manager == null) {
                throw new ManagerNotFoundException("No se ha encontrado el manager con el ID " + user.getUserID());
            }

            if (!isManagerInStab(targetStab, manager)) {
                throw new ManagerNotFromStablishmentException("El manager con el ID " + user.getUserID() + " no es manager del establecimiento con el ID " + targetStab.getStablishmentID());
            }
        }

        stablishment.setCapacity(targetStab.getCapacity());
        stablishment.setStabAddress(targetStab.getStabAddress());
        stablishment.setCloseTime(targetStab.getCloseTime());
        stablishment.setOpenTime(targetStab.getOpenTime());
        stablishment.setStabName(targetStab.getStabName());
        stabRepo.save(stablishment);
    }

    /**
     * Añade un manager a un establecimiento.
     * @param stablishmentID el ID del establecimiento.
     * @param userID el ID del usuario que se va a añadir como manager.
     * @param token el token del manager.
     * @throws StablishmentNotFoundException si no se encuentra el establecimiento.
     * @throws UserNotFoundException si no se encuentra el usuario.
     * @throws ManagerNotFoundException si no se encuentra el manager.
     * @throws ManagerNotFromStablishmentException si el manager no pertenece al establecimiento.
     */
    public void addManagerToStab(Long stablishmentID, String userID, String token){
        String userId = jwtService.extractUserIDFromToken(token);

        stablishment targetStab = stabRepo.findById(stablishmentID)
            .orElseThrow(() -> new StablishmentNotFoundException("No se ha encontrado el establecimiento con el ID " + stablishmentID));

        user targetUser = userRepo.findById(userID)
            .orElseThrow(() -> new UserNotFoundException("No se ha encontrado el usuario con el ID " + userID));

        manager stabManager = managerRepo.findByUserID(userRepo.findById(userId).orElse(null))
            .orElseThrow(() -> new ManagerNotFoundException("No se ha encontrado el manager con el ID " + userId));

        if (!isManagerInStab(targetStab, stabManager)) {
            throw new ManagerNotFromStablishmentException("El establecimiento con el ID " + targetStab.getStablishmentID() + " no pertenece al manager con el ID " + userId);
        }
        if (stabManager.isOwner()){
            manager newManager = new manager();
            targetUser.setUserRole(role.MANAGER);
            newManager.setUserID(targetUser);
            newManager.setOwner(false);
            newManager.setStablishmentID(targetStab);

            targetStab.getManagerID().add(newManager);
            managerRepo.save(newManager);
            stabRepo.save(targetStab);
        }
    }

    public void addOwner(Long stablishmentID, String userID, String token){
        String userId = jwtService.extractUserIDFromToken(token);
        user requestUser = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No se ha encontrado el usuario con el ID " + userId));
        stablishment targetStab = stabRepo.findById(stablishmentID)
                .orElseThrow(() -> new StablishmentNotFoundException("No se ha encontrado el establecimiento con el ID " + stablishmentID));

        user targetUser = userRepo.findById(userID)
                .orElseThrow(() -> new UserNotFoundException("No se ha encontrado el usuario con el ID " + userID));


        if (requestUser.getUserRole() == role.ADMIN) {
            manager newManager = new manager();
            targetUser.setUserRole(role.MANAGER);
            newManager.setUserID(targetUser);
            newManager.setOwner(true);
            newManager.setStablishmentID(targetStab);

            targetStab.getManagerID().add(newManager);
            managerRepo.save(newManager);
            stabRepo.save(targetStab);
        }
    }

    /**
     * Comprueba si un manager pertenece a un establecimiento.
     * @param targetStab el establecimiento.
     * @param stabManager el manager.
     * @return true si el manager pertenece al establecimiento, false en caso contrario.
     */
    public boolean isManagerInStab(stablishment targetStab, manager stabManager) {
        return targetStab.getManagerID().contains(stabManager);
    }
}
