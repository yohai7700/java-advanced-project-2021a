package openu.advanced.java_workshop.beans.secured.admin;

import openu.advanced.java_workshop.WorkshopDatabase;
import openu.advanced.java_workshop.model.CategoriesEntity;
import openu.advanced.java_workshop.model.CategoryMembersEntity;
import openu.advanced.java_workshop.model.GamesEntity;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * A bean used for the categories management page.
 */
@Named
@ViewScoped
public class CategoriesManagementBean {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = WorkshopDatabase.getEntityManagerFactory();
    private CategoriesEntity membersDialogCategory;
    private String newCategoryName;
    private String newCategoryMember;

    public String getNewCategoryMember() {
        return newCategoryMember;
    }

    public void setNewCategoryMember(String newCategoryMember) {
        this.newCategoryMember = newCategoryMember;
    }

    public String getNewCategoryName() {
        return newCategoryName;
    }

    public void setNewCategoryName(String newCategoryName) {
        this.newCategoryName = newCategoryName;
    }

    /**
     * @return the category of the category members dialog if exists
     */
    public CategoriesEntity getMembersDialogCategory() {
        return membersDialogCategory;
    }

    /**
     * Sets the current category for the category members dialog
     *
     * @param membersDialogCategory the category that has been chosen to edit
     */
    public void setCategoryMembersDialog(CategoriesEntity membersDialogCategory) {
        this.membersDialogCategory = membersDialogCategory;
    }

    /**
     * Action listener for opening the category members dialog
     *
     * @param category the category that has been chosen to edit
     */
    public void openCategoryMembersDialog(CategoriesEntity category) {
        setCategoryMembersDialog(category);
    }

    public List<GamesEntity> getCategoryDialogMembers() {
        if (membersDialogCategory == null) return null;
        return getGamesInCategory(membersDialogCategory.getId());
    }

    /**
     * @return all the categories on the website.
     */
    public List<CategoriesEntity> getCategories() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<CategoriesEntity> findAllCategories = entityManager.createNamedQuery("findAllCategories", CategoriesEntity.class);
        return findAllCategories.getResultList();
    }

    /**
     * Counts all the games in a given category.
     *
     * @param categoryId the category id for the category the games are in.
     * @return all games in the category
     */
    public List<GamesEntity> getGamesInCategory(int categoryId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<GamesEntity> findGamesByCategoryId = entityManager.createNamedQuery("findGamesByCategoryId", GamesEntity.class);
        List<GamesEntity> games = findGamesByCategoryId
                .setParameter("categoryId", categoryId)
                .getResultList();
        return games;
    }

    /**
     * Return the amount of games in a given category.
     *
     * @param categoryId the category id for the category the games are counter for.
     * @return amount of games in the category
     */
    public int getGamesAmountInCategory(int categoryId) {
        return getGamesInCategory(categoryId).size();
    }

    /**
     * Deletes given category from DB
     *
     * @param categoryId the category to delete
     */
    public void deleteCategory(int categoryId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        CategoriesEntity category = entityManager.find(CategoriesEntity.class, categoryId);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // Deletes the coupon
        if (category != null && entityManager.contains(category)) {
            entityManager.remove(category);
        }
        deleteCategoryMembersOfCategory(entityManager, categoryId);
        transaction.commit();
    }

    /**
     * Deletes category member of the category members dialog category
     *
     * @param gameId game id of the member to delete
     */
    public void deleteCategoryDialogMember(int gameId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        CategoryMembersEntity member = new CategoryMembersEntity();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        member.setGameId(gameId);
        member.setCategoryId(membersDialogCategory.getId());
        entityManager.remove(entityManager.contains(member) ? member : entityManager.merge(member));
        transaction.commit();
    }

    /**
     * Add a new category by the current category name field.
     */
    public void addCategory() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        CategoriesEntity category = new CategoriesEntity();
        category.setName(newCategoryName);
        entityManager.persist(category);
        transaction.commit();
    }

    /**
     * Add new member by the category members dialog.
     */
    public void addCategoryMember() {
        try {
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            int gameId = Integer.parseInt(newCategoryMember);
            CategoryMembersEntity categoryMember = new CategoryMembersEntity();
            categoryMember.setCategoryId(membersDialogCategory.getId());
            categoryMember.setGameId(gameId);
            entityManager.persist(categoryMember);
            transaction.commit();
        } catch (Exception exception) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure Occurred", "Game ID is not valid");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    /**
     * Deletes all games in the given category.
     *
     * @param entityManager entityManager to use for deletion in order to allow external transactions
     * @param categoryId    the given category to delete games for
     */
    private void deleteCategoryMembersOfCategory(EntityManager entityManager, int categoryId) {
        List<GamesEntity> games = getGamesInCategory(categoryId);
        for (GamesEntity game : games) {
            CategoryMembersEntity categoryMember = new CategoryMembersEntity();
            categoryMember.setCategoryId(categoryId);
            categoryMember.setGameId(game.getId());
            if (entityManager.contains(categoryMember)) {
                entityManager.remove(categoryMember);
            }
        }
    }
}
