package com.investadvisor.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for {@link PeopleResource}.
 */
//@RunWith(MockitoJUnitRunner.class)
public class PeopleResourceTest {
   /* private static final PersonDAO dao = mock(PersonDAO.class);
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new PeopleResource(dao))
            .build();
    @Captor
    private ArgumentCaptor<Person> personCaptor;
    private Person person;

    @Before
    public void setUp() {
        person = new Person();
        person.setFullName("Full Name");
        person.setJobTitle("Job Title");
    }

    @After
    public void tearDown() {
        reset(dao);
    }

    @Test
    public void createPerson() throws JsonProcessingException {
        when(dao.create(any(Person.class))).thenReturn(person);
        final Response response = resources.client().target("/people")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(person, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(dao).create(personCaptor.capture());
        assertThat(personCaptor.getValue()).isEqualTo(person);
    }

    @Test
    public void listPeople() throws Exception {
        final ImmutableList<Person> people = ImmutableList.of(person);
        when(dao.findAll()).thenReturn(people);

        final List<Person> response = resources.client().target("/people")
                .request().get(new GenericType<List<Person>>() {});

        verify(dao).findAll();
        assertThat(response).containsAll(people);
    }  */
}
